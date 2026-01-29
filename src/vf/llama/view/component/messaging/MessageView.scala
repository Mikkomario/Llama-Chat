package vf.llama.view.component.messaging

import utopia.echo.model.ChatMessage
import utopia.echo.model.enumeration.ChatRole
import utopia.echo.model.enumeration.ChatRole.{Assistant, System, User}
import utopia.echo.model.response.Reply
import utopia.firmament.context.text.StaticTextContext
import utopia.firmament.localization.LocalString._
import utopia.firmament.model.stack.LengthPriority.Low
import utopia.firmament.model.stack.{StackLength, StackSize}
import utopia.flow.collection.immutable.Pair
import utopia.flow.view.immutable.eventful.Fixed
import utopia.flow.view.template.eventful.Changing
import utopia.genesis.util.Screen.ppi
import utopia.paradigm.measurement.DistanceExtensions._
import utopia.reach.component.factory.ContextualComponentFactories.CCF
import utopia.reach.component.factory.Mixed
import utopia.reach.component.factory.contextual.TextContextualFactory
import utopia.reach.component.hierarchy.ComponentHierarchy
import utopia.reach.component.label.empty.ViewEmptyLabel
import utopia.reach.component.label.text.selectable.SelectableTextLabel
import utopia.reach.component.template.{ReachComponent, ReachComponentWrapper}
import utopia.reach.container.multi.Stack
import utopia.reach.container.wrapper.Framing
import vf.llama.view.component.messaging.MessageView.userRoles

case class MessageViewFactory(hierarchy: ComponentHierarchy, context: StaticTextContext)
	extends TextContextualFactory[MessageViewFactory]
{
	// IMPLEMENTED  -----------------------
	
	override def self: MessageViewFactory = this
	
	override def withContext(context: StaticTextContext): MessageViewFactory = copy(context = context)
	
	
	// OTHER    ---------------------------
	
	def apply(messageP: Changing[Either[ChatMessage, Reply]]) = new MessageView(hierarchy, context, messageP)
}

object MessageView extends CCF[StaticTextContext, MessageViewFactory]
{
	// ATTRIBUTES   -----------------------
	
	private val userRoles = Set[ChatRole](User, System)
	
	
	// IMPLEMENTED  -----------------------
	
	override def withContext(hierarchy: ComponentHierarchy, context: StaticTextContext): MessageViewFactory =
		MessageViewFactory(hierarchy, context)
}
/**
 * Used for displaying a (streaming) chat message
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
class MessageView(override val hierarchy: ComponentHierarchy, context: StaticTextContext,
                  messageP: Changing[Either[ChatMessage, Reply]])
	extends ReachComponentWrapper
{
	// ATTRIBUTES   ----------------------
	
	private lazy val senderMargin = StackLength(context.margins.small, 2.cm.toPixels, priority = Low)
	
	private val textP = messageP.flatMapWhile(linkedFlag) {
		case Left(message) => Fixed(message.text)
		case Right(reply) =>
			// Displays either the thinking or the text contents
			if (reply.thinking)
				reply.thinkingFlag.flatMap { if (_) reply.thoughtsPointer else reply.textPointer }
			else
				reply.textPointer
	}
	private val roleP = messageP.mapWhile(linkedFlag) {
		case Left(message) => message.senderRole
		case Right(_) => Assistant
	}
	private val userRoleFlag = roleP.map { role => userRoles.contains(role) }
	
	// [Margin | Message | Margin]
	// The side of the applied margin is determined by sender role
	override protected lazy val wrapped: ReachComponent = Stack.withContext(hierarchy, context).row.withoutMargin
		.build(Mixed) { factories =>
			// Constructs the margin labels on each side
			val margins = Pair(true, false).map { isUser =>
				val sizeP = userRoleFlag.map { u =>
					val width = if (u == isUser) senderMargin else StackLength.fixedZero
					StackSize(width, StackLength.any.lowPriority)
				}
				factories(ViewEmptyLabel)(sizeP)
			}
			
			// Constructs the main message view, which is framed in a round "bubble"
			val messageBg = factories.context.color.light.gray
			val messageView = factories(Framing).small.rounded(messageBg)
				.build(SelectableTextLabel) { labelF =>
					// Displays thinking content with a different color / alpha
					val defaultTextColorP = labelF.context.textColorPointer
					val textColorP = messageP.flatMapWhile(linkedFlag) {
						case Left(_) => defaultTextColorP
						case Right(reply) =>
							reply.thinkingFlag.flatMap { thinking =>
								if (thinking)
									defaultTextColorP.map { _.timesAlpha(0.66) }
								else
									defaultTextColorP
							}
					}
					labelF.withTextColorPointer(textColorP).manyLines(textP.map { _.noLanguage.skipLocalization })
				}
				.parent
			
			Vector(margins.first, messageView, margins.second)
		}
}
