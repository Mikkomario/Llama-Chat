package vf.llama.view.vc.mvp

import utopia.annex.util.RequestResultExtensions._
import utopia.echo.controller.Chat
import utopia.echo.model.ChatMessage
import utopia.echo.model.enumeration.ChatRole.User
import utopia.echo.model.llm.LlmDesignator
import utopia.echo.model.response.ollama.chat.ReplyMessage
import utopia.firmament.context.text.StaticTextContext
import utopia.firmament.model.enumeration.SizeCategory.Small
import utopia.flow.collection.CollectionExtensions._
import utopia.flow.collection.immutable.Pair
import utopia.flow.time.TimeExtensions._
import utopia.flow.util.StringExtensions._
import utopia.flow.util.TryExtensions._
import utopia.flow.view.mutable.async.Volatile
import utopia.flow.view.mutable.eventful.AssignableOnce
import utopia.flow.view.template.eventful.Flag
import utopia.genesis.util.Screen
import utopia.paradigm.enumeration.Axis
import utopia.reach.component.factory.{ContextualMixed, Mixed}
import utopia.reach.container.multi.Stack
import utopia.reach.container.wrapper.{Framing, Swapper}
import utopia.reach.window.ReachWindow
import vf.llama.util.Common._
import vf.llama.view.CommonView._
import vf.llama.view.component.messaging.{MessagesView, SendMessageView}
import vf.llama.view.component.{SetupView, WindowHeader}

/**
 * Used for managing the chat context for interacting with an LLM
 * @author Mikko Hilpinen
 * @since 19.07.2025, v0.1
 */
object ChatVc
{
	// ATTRIBUTES   ------------------------------
	
	private val chatP = AssignableOnce[Chat]()
	private val streamingMessageP = Volatile.eventful.empty[(ChatMessage, ReplyMessage)]
	private val sendingFlag: Flag = streamingMessageP.lightMap { _.isDefined }
	
	private lazy val messageLineSplit = Screen.width * 0.4
	
	
	// OTHER    ----------------------------------
	
	def display() = {
		// Loads the available LLMs first
		ollama.localModels.future.mapSuccess { llmInfo =>
			// Contains 2 vertical elements:
			//      1. Window header
			//      2. Window content, which may be switched between setup & main view
			println("Creating the window")
			val window = ReachWindow.withContext(windowContext).using(Stack) { (_, stackF) =>
				stackF.withoutMargin.build(Mixed) { factories =>
					val header = factories(WindowHeader).withBackground(color.primary.dark)()
					// The content is framed
					val content = factories(Framing).withInsetsAlong(Axis.Y, Small)
						.withBackground(color.primary.default)
						.build(Swapper) { swapF =>
							swapF.notCaching.build(Mixed)(chatP) { (factories, chat) =>
								chat match {
									case Some(chat) => createContentView(factories, chat)
									case None => createSetup(factories, llmInfo.map { info => LlmDesignator(info.name) })
								}
							}
						}
						.parent
					
					Pair(header, content)
				}
			}
			
			println("Displaying the window")
			window.display(centerOnParent = true)
			window.setToExitOnClose()
			window.setToCloseOnEsc()
			
			window.window
		}
	}
	
	private def createSetup(factories: ContextualMixed[StaticTextContext], llms: Seq[LlmDesignator]) =
	{
		factories(SetupView).withInputWidth(messageLineSplit)(llms) { (llm, system, firstMessage) =>
			// Sets up the chat and sends the first message
			val chat = new Chat(ollama, llm)
			chat.systemMessages = system.ifNotEmpty.emptyOrSingle
			send(chat, firstMessage)
			chatP.set(chat)
		}
	}
	
	private def createContentView(factories: ContextualMixed[StaticTextContext], chat: Chat) = {
		// Forms the pointer for the displayed messages
		val messagesP = chat.messageHistoryPointer
			.mergeWith(streamingMessageP) { (history, streaming) =>
				val _history = history.map { Left(_) }
				streaming match {
					case Some((out, streamingReply)) => _history ++ Pair(Left(out), Right(streamingReply))
					case None => _history
				}
			}
			.delayedBy(0.1.seconds)
		
		// Contains 2 vertical elements:
		//      1. Messages (scrollable)
		//      2. Send message -component
		factories(Stack)
			.build(Mixed) { factories =>
				val messagesView = factories(MessagesView).withLineSplitThreshold(messageLineSplit).apply(messagesP)
				val sendView = factories(SendMessageView).withInputWidth(messageLineSplit).withEnabledFlag(!sendingFlag)
					.apply { send(chat, _) }
				
				Pair(messagesView, sendView)
			}
			.parent
	}
	
	private def send(chat: Chat, message: String) = {
		val schrodinger = chat.push(message)
		streamingMessageP.setOne(User(message) -> schrodinger.manifest)
		schrodinger.finalResultFuture.foreach { result =>
			result.wrapped.logWithMessage("Failed to send a chat message")
			streamingMessageP.clear()
		}
	}
}
