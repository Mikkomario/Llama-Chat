package vf.llama.view

import utopia.firmament.context.base.StaticBaseContext
import utopia.firmament.context.{AnimationContext, ScrollingContext}
import utopia.firmament.localization.{Language, Localizer, NoLocalization}
import utopia.firmament.model.Margins
import utopia.flow.async.context.ThreadPool
import utopia.flow.util.logging.{Logger, SysErrLogger}
import utopia.genesis.handling.action.{ActionLoop, ActorHandler}
import utopia.genesis.text.Font
import utopia.genesis.util.Screen.ppi
import utopia.paradigm.measurement.DistanceExtensions._
import utopia.reach.context.ReachWindowContext

/**
 * Provides commonly used values throughout the project
 *
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
object CommonView
{
	// ATTRIBUTES   ---------------------
	
	implicit val log: Logger = SysErrLogger
	implicit val exc: ThreadPool = new ThreadPool("Echo-Chat")
	
	implicit val language: Language = Language.english
	implicit val localizer: Localizer = NoLocalization
	
	val actorHandler = ActorHandler()
	private val actionLoop = new ActionLoop(actorHandler)
	
	private val font = Font("Roboto", 12)
	val margins = Margins(0.2.cm.toPixels)
	val windowContext = ReachWindowContext(actorHandler, color.gray).borderless
		.withContentContext(StaticBaseContext(actorHandler, font, color.scheme, margins)
			.against(color.gray).forTextComponents)
	
	implicit val sc: ScrollingContext = ScrollingContext.withDarkRoundedBar(actorHandler)
	implicit val ac: AnimationContext = AnimationContext(actorHandler)
	
	
	// INITIAL CODE ---------------------
	
	actionLoop.runAsync()
	
	
	// COMPUTED -------------------------
	
	def icon = Icons
	def color = Colors
}
