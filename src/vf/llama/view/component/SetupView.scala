package vf.llama.view.component

import utopia.echo.model.llm.LlmDesignator
import utopia.firmament.context.text.StaticTextContext
import utopia.firmament.localization.LocalString._
import utopia.firmament.model.stack.LengthExtensions._
import utopia.flow.collection.immutable.Pair
import utopia.flow.view.mutable.Pointer
import utopia.flow.view.mutable.eventful.ResettableFlag
import utopia.reach.component.factory.ContextualComponentFactories.CCF
import utopia.reach.component.factory.Mixed
import utopia.reach.component.factory.contextual.TextContextualFactory
import utopia.reach.component.hierarchy.ComponentHierarchy
import utopia.reach.component.interactive.input.check.Switch
import utopia.reach.component.interactive.input.selection.RadioButtonGroup
import utopia.reach.component.interactive.input.text.TextField
import utopia.reach.component.label.text.TextLabel
import utopia.reach.component.template.{ReachComponent, ReachComponentWrapper}
import utopia.reach.container.multi.Stack
import vf.llama.view.CommonView._
import vf.llama.view.component.messaging.{SendMessageView, SendMessageViewSettings, SendMessageViewSettingsWrapper}

case class SetupViewFactory(hierarchy: ComponentHierarchy, context: StaticTextContext,
                            settings: SendMessageViewSettings = SendMessageViewSettings.default)
	extends TextContextualFactory[SetupViewFactory] with SendMessageViewSettingsWrapper[SetupViewFactory]
{
	// IMPLEMENTED  -------------------------
	
	override def self: SetupViewFactory = this
	
	override def withSettings(settings: SendMessageViewSettings): SetupViewFactory = copy(settings = settings)
	override def withContext(context: StaticTextContext): SetupViewFactory = copy(context = context)
	
	
	// OTHER    -----------------------------
	
	def apply(llms: Seq[LlmDesignator])(complete: (LlmDesignator, String, String) => Unit) =
		new SetupView(hierarchy, context, llms, settings)(complete)
}

object SetupView extends CCF[StaticTextContext, SetupViewFactory]
{
	override def withContext(hierarchy: ComponentHierarchy, context: StaticTextContext): SetupViewFactory =
		SetupViewFactory(hierarchy, context)
}
/**
 * Provides an interface for setting up the software by selecting an LLM,
 * specifying a system message and sending the first prompt.
 *
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
class SetupView(override val hierarchy: ComponentHierarchy, context: StaticTextContext, llms: Seq[LlmDesignator],
                sendSettings: SendMessageViewSettings)
               (onComplete: (LlmDesignator, String, String) => Unit)
	extends ReachComponentWrapper
{
	// ATTRIBUTES   ------------------------
	
	private val llmP = Pointer.eventful(llms.head)
	private val thinksFlag = ResettableFlag()
	
	
	// IMPLEMENTED  ------------------------
	
	// Contains 3 vertical layers of components:
	//      1. LLM selection via radio buttons
	//      2. System message input
	//      3. First message input
	override protected lazy val wrapped: ReachComponent = Stack
		.withContext(hierarchy, context.withLineSplitThreshold(sendSettings.inputWidth))
		.leading
		.build(Mixed) { factories =>
			// The LLM section contains 3 vertical elements:
			//      1. Header
			//      2. Radio buttons for selecting the LLM
			//      3. A switch for toggling thinking mode
			val llmArea = factories(Stack).related.leading
				.build(Mixed) { factories =>
					val header = factories(TextLabel)("LLM")
					val selection = factories(RadioButtonGroup)
						.apply(llms.map { llm => (llm, llm.llmName.noLanguage.skipLocalization) }, llmP)
					
					val thinkArea = factories(Stack).related.centeredRow
						.build(Mixed) { factories =>
							val label = factories(TextLabel)("Thinking model:")
							val thinksSwitch = factories(Switch)(thinksFlag)
							
							Pair(label, thinksSwitch)
						}
						.parent
					
					Vector(header, selection, thinkArea)
				}
				.parent
			
			val systemInput = factories(TextField).withFieldName("System message").withHint("Optional")
				.string(sendSettings.inputWidth.any)
			val messageInput = factories(SendMessageView).withSettings(sendSettings).apply { message =>
				val baseLlm = llmP.value
				onComplete(if (thinksFlag.value) baseLlm.thinking else baseLlm, systemInput.value, message)
			}
			
			Vector(llmArea, systemInput, messageInput)
		}
}
