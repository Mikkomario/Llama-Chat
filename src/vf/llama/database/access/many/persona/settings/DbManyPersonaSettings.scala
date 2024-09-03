package vf.llama.database.access.many.persona.settings

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.persona.PersonaSettings

/**
  * The root access point when targeting multiple persona settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbManyPersonaSettings 
	extends ManyPersonaSettingsAccess with NonDeprecatedView[PersonaSettings] 
		with ViewManyByIntIds[ManyPersonaSettingsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) persona settings
	  */
	def includingHistory = DbPersonaSettingsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbPersonaSettingsIncludingHistory extends ManyPersonaSettingsAccess with UnconditionalView
}

