package vf.llama.view.component.messaging

import utopia.firmament.context.text.StaticTextContext
import utopia.firmament.model.stack.LengthExtensions._
import utopia.flow.collection.immutable.Pair
import utopia.flow.util.Mutate
import utopia.flow.view.immutable.eventful.AlwaysTrue
import utopia.flow.view.mutable.Pointer
import utopia.flow.view.template.eventful.Flag
import utopia.genesis.util.Screen
import utopia.paradigm.color.ColorRole.Secondary
import utopia.reach.component.factory.ContextualComponentFactories.CCF
import utopia.reach.component.factory.Mixed
import utopia.reach.component.factory.contextual.TextContextualFactory
import utopia.reach.component.hierarchy.ComponentHierarchy
import utopia.reach.component.interactive.button.image.ImageButton
import utopia.reach.component.interactive.input.text.TextField
import utopia.reach.component.template.{PartOfComponentHierarchy, ReachComponent, ReachComponentWrapper}
import utopia.reach.container.multi.Stack
import vf.llama.view.CommonView._

/**
 * Common trait for send message view factories and settings
 * @tparam Repr Implementing factory/settings type
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
trait SendMessageViewSettingsLike[+Repr]
{
	// ABSTRACT	--------------------
	
	/**
	 * Default width of the message input field
	 */
	def inputWidth: Double
	/**
	 * A flag that determines when the send button is enabled
	 */
	def enabledFlag: Flag
	
	/**
	 * A flag that determines when the send button is enabled
	 * @param flag New enabled flag to use.
	 *             A flag that determines when the send button is enabled
	 * @return Copy of this factory with the specified enabled flag
	 */
	def withEnabledFlag(flag: Flag): Repr
	/**
	 * Default width of the message input field
	 * @param width New input width to use.
	 *              Default width of the message input field
	 * @return Copy of this factory with the specified input width
	 */
	def withInputWidth(width: Double): Repr
	
	
	// OTHER	--------------------
	
	def mapEnabledFlag(f: Mutate[Flag]) = withEnabledFlag(f(enabledFlag))
}

object SendMessageViewSettings
{
	// ATTRIBUTES	--------------------
	
	val default = apply()
}

/**
 * Combined settings used when constructing send message views
 * @param inputWidth  Default width of the message input field
 * @param enabledFlag A flag that determines when the send button is enabled
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
case class SendMessageViewSettings(inputWidth: Double = Screen.width * 0.3, enabledFlag: Flag = AlwaysTrue)
	extends SendMessageViewSettingsLike[SendMessageViewSettings]
{
	// IMPLEMENTED	--------------------
	
	override def withEnabledFlag(flag: Flag) = copy(enabledFlag = flag)
	override def withInputWidth(width: Double) = copy(inputWidth = width)
}

/**
 * Common trait for factories that wrap a send message view settings instance
 * @tparam Repr Implementing factory/settings type
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
trait SendMessageViewSettingsWrapper[+Repr] extends SendMessageViewSettingsLike[Repr]
{
	// ABSTRACT	--------------------
	
	/**
	 * Settings wrapped by this instance
	 */
	protected def settings: SendMessageViewSettings
	
	/**
	 * @return Copy of this factory with the specified settings
	 */
	def withSettings(settings: SendMessageViewSettings): Repr
	
	
	// IMPLEMENTED	--------------------
	
	override def enabledFlag = settings.enabledFlag
	override def inputWidth = settings.inputWidth
	
	override def withEnabledFlag(flag: Flag) = mapSettings { _.withEnabledFlag(flag) }
	override def withInputWidth(width: Double) = mapSettings { _.withInputWidth(width) }
	
	
	// OTHER	--------------------
	
	def mapSettings(f: SendMessageViewSettings => SendMessageViewSettings) = withSettings(f(settings))
}

/**
 * Factory class used for constructing send message views using contextual component creation
 * information
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
case class ContextualSendMessageViewFactory(hierarchy: ComponentHierarchy, context: StaticTextContext,
                                            settings: SendMessageViewSettings = SendMessageViewSettings.default)
	extends SendMessageViewSettingsWrapper[ContextualSendMessageViewFactory]
		with TextContextualFactory[ContextualSendMessageViewFactory]
		with PartOfComponentHierarchy
{
	// IMPLEMENTED	--------------------
	
	override def self = this
	
	override def withContext(context: StaticTextContext) = copy(context = context)
	override def withSettings(settings: SendMessageViewSettings) = copy(settings = settings)
	
	
	// OTHER    ------------------------
	
	/**
	 * @param send A function called when the send button is pressed.
	 *             Receives the currently typed message.
	 * @return A new send message view
	 */
	def apply(send: String => Unit) = new SendMessageView(hierarchy, context, settings)(send)
}

/**
 * Used for defining send message view creation settings outside the component building process
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
case class SendMessageViewSetup(settings: SendMessageViewSettings = SendMessageViewSettings.default)
	extends SendMessageViewSettingsWrapper[SendMessageViewSetup]
		with CCF[StaticTextContext, ContextualSendMessageViewFactory]
{
	// IMPLEMENTED	--------------------
	
	override def withContext(hierarchy: ComponentHierarchy, context: StaticTextContext) =
		ContextualSendMessageViewFactory(hierarchy, context, settings)
	override def withSettings(settings: SendMessageViewSettings) = copy(settings = settings)
}

object SendMessageView extends SendMessageViewSetup()
{
	// OTHER	--------------------
	
	def apply(settings: SendMessageViewSettings) = withSettings(settings)
}

/**
 * An interface for writing and sending a chat message
 *
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
class SendMessageView(override val hierarchy: ComponentHierarchy, context: StaticTextContext,
                      settings: SendMessageViewSettings)
                     (send: String => Unit)
	extends ReachComponentWrapper
{
	// ATTRIBUTES   ----------------------
	
	private val messageP = Pointer.eventful("")
	//noinspection EmptyCheck
	private val hasMessageFlag: Flag = messageP.lightMap { !_.isEmpty }
	
	// [Input | Send]
	override protected lazy val wrapped: ReachComponent = Stack.withContext(hierarchy, context).related.trailing.row
		.build(Mixed) { factories =>
			val inputField = factories(TextField).withFieldName("Message")
				.manyLines.withLineSplitThreshold(settings.inputWidth)
				.string(settings.inputWidth.any, messageP)
			val sendButton = factories(ImageButton).withEnabledFlag(hasMessageFlag && settings.enabledFlag)
				.icon(icon.send, Some(Secondary)) { send(messageP.getAndSet("")) }
			
			Pair(inputField, sendButton)
		}
}
