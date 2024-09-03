package vf.llama.database.access.many.persona.settings

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.persona.PersonaSettingsWithParameters

/**
  * The root access point when targeting multiple persona settings with parameters at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbManyPersonaSettingsWithParameters 
	extends ManyPersonaSettingsWithParametersAccess with NonDeprecatedView[PersonaSettingsWithParameters] 
		with ViewManyByIntIds[ManyPersonaSettingsWithParametersAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) persona settings 
		with parameters
	  */
	def includingHistory = DbPersonaSettingsWithParametersIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbPersonaSettingsWithParametersIncludingHistory 
		extends ManyPersonaSettingsWithParametersAccess with UnconditionalView
}

