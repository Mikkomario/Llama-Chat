package vf.llama.view

import utopia.firmament.localization.{Language, Localizer, NoLocalization}

/**
 * Provides commonly used values throughout the project
 *
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
object CommonView
{
	// ATTRIBUTES   ---------------------
	
	implicit val language: Language = Language.english
	implicit val localizer: Localizer = NoLocalization
	
	
	// COMPUTED -------------------------
	
	def icon = Icons
}
