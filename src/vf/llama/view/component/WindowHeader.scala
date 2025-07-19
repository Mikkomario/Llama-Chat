package vf.llama.view.component

import utopia.firmament.context.text.StaticTextContext
import utopia.firmament.localization.LocalizedString
import utopia.flow.collection.immutable.Pair
import utopia.flow.view.immutable.eventful.Fixed
import utopia.flow.view.template.eventful.Changing
import utopia.reach.component.factory.FromContextComponentFactoryFactory.Ccff
import utopia.reach.component.factory.Mixed
import utopia.reach.component.factory.contextual.TextContextualFactory
import utopia.reach.component.hierarchy.ComponentHierarchy
import utopia.reach.component.interactive.button.image.ImageButton
import utopia.reach.component.label.text.ViewTextLabel
import utopia.reach.component.template.{ReachComponent, ReachComponentWrapper}
import utopia.reach.container.multi.Stack
import utopia.reach.container.wrapper.Framing
import utopia.reach.cursor.DragTo
import vf.llama.util.Common._
import vf.llama.view.CommonView._

case class WindowHeaderFactory(hierarchy: ComponentHierarchy, context: StaticTextContext,
                               titleP: Changing[LocalizedString] = LocalizedString.alwaysEmpty)
	extends TextContextualFactory[WindowHeaderFactory]
{
	// IMPLEMENTED  ---------------------
	
	override def self: WindowHeaderFactory = this
	
	override def withContext(context: StaticTextContext): WindowHeaderFactory = copy(context = context)
	
	
	// OTHER    -------------------------
	
	def withTitlePointer(p: Changing[LocalizedString]) = copy(titleP = p)
	def withTitle(title: LocalizedString) = withTitlePointer(Fixed(title))
	
	def apply() = new WindowHeader(hierarchy, context, titleP)
}

object WindowHeader extends Ccff[StaticTextContext, WindowHeaderFactory]
{
	override def withContext(hierarchy: ComponentHierarchy, context: StaticTextContext): WindowHeaderFactory =
		WindowHeaderFactory(hierarchy, context)
}

/**
 * A header component displayed at the top of a window
 *
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
class WindowHeader(override val hierarchy: ComponentHierarchy, context: StaticTextContext,
                   titleP: Changing[LocalizedString])
	extends ReachComponentWrapper
{
	// ATTRIBUTES  -----------------------
	
	// Contains small margins
	// Specifies a background color
	override protected lazy val wrapped: ReachComponent =
		Framing.withContext(hierarchy, context).verySmall.withBackground(context.background.darkened)
			// [Title | Close]
			.build(Stack) { stackF =>
				stackF.centeredRow.mapContext { _.mapStackMargin { _.expanding } }.build(Mixed) { factories =>
					val titleLabel = factories(ViewTextLabel).text(titleP)
					val closeButton = factories(ImageButton).icon(icon.close) {
						context.windowPointer.value match {
							case Some(window) => window.close()
							case None => System.exit(0)
						}
					}
					
					Pair(titleLabel, closeButton)
				}
			}
			
	
	// INITIAL CODE ---------------------
	
	linkedFlag.onceSet { DragTo.repositionWindow.expandingAtSides.fillingAtTop.applyTo(wrapped) }
}
