package vf.llama.view.component.messaging

import utopia.echo.model.ChatMessage
import utopia.echo.model.response.ollama.chat.ReplyMessage
import utopia.firmament.context.ScrollingContext
import utopia.firmament.context.text.StaticTextContext
import utopia.flow.view.template.eventful.Changing
import utopia.reach.component.factory.contextual.TextContextualFactory
import utopia.reach.component.hierarchy.ComponentHierarchy
import utopia.reach.component.template.{ReachComponent, ReachComponentWrapper}
import utopia.reach.container.multi.ViewStack
import utopia.reach.container.wrapper.scrolling.ScrollView

case class MessagesViewFactory(hierarchy: ComponentHierarchy, context: StaticTextContext)
	extends TextContextualFactory[MessagesViewFactory]
{
	// IMPLEMENTED  --------------------------
	
	override def self: MessagesViewFactory = this
	
	override def withContext(context: StaticTextContext): MessagesViewFactory = copy(context = context)
	
	
	// OTHER    ------------------------------
	
	def apply(messagesP: Changing[Seq[Either[ChatMessage, ReplyMessage]]])(implicit sc: ScrollingContext) =
		new MessagesView(hierarchy, context, messagesP)
}

/**
 * A view which displays n chat messages
 *
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
class MessagesView(override val hierarchy: ComponentHierarchy, context: StaticTextContext,
                   messagesP: Changing[Seq[Either[ChatMessage, ReplyMessage]]])
                  (implicit sc: ScrollingContext)
	extends ReachComponentWrapper
{
	// ATTRIBUTES  -------------------------
	
	// The main view is scrollable
	private lazy val view = ScrollView.withContext(hierarchy, context).initialized
		.build(ViewStack) { stackF =>
			// n message views are stacked on top of each other
			stackF.mapPointer(messagesP, MessageView) { (viewF, messageP, _) => viewF(messageP) }
		}
		.parent
	
	
	// IMPLEMENTED  ------------------------
	
	override protected def wrapped: ReachComponent = view
}
