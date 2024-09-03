package vf.llama.database.access.single.persona

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaWithSettingsDbFactory
import vf.llama.database.storable.persona.{PersonaDbModel, PersonaSettingsDbModel}
import vf.llama.model.combined.persona.PersonaWithSettings

/**
  * Used for accessing individual personas with settings
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaWithSettings 
	extends SingleRowModelAccess[PersonaWithSettings] with NonDeprecatedView[PersonaWithSettings] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked settings
	  */
	protected def settingsModel = PersonaSettingsDbModel
	
	/**
	  * A database model (factory) used for interacting with linked personas
	  */
	private def model = PersonaDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaWithSettingsDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona with settings
	  * @return An access point to that persona with settings
	  */
	def apply(id: Int) = DbSinglePersonaWithSettings(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield unique personas 
	  * with settings.
	  * @return An access point to the persona with settings that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniquePersonaWithSettingsAccess(mergeCondition(additionalCondition))
}

