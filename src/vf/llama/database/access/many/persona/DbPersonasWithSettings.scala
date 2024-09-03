package vf.llama.database.access.many.persona

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.persona.PersonaWithSettings

/**
  * The root access point when targeting multiple personas with settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonasWithSettings 
	extends ManyPersonasWithSettingsAccess with NonDeprecatedView[PersonaWithSettings] 
		with ViewManyByIntIds[ManyPersonasWithSettingsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) personas with settings
	  */
	def includingHistory = DbPersonasWithSettingsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbPersonasWithSettingsIncludingHistory extends ManyPersonasWithSettingsAccess 
		with UnconditionalView
}

