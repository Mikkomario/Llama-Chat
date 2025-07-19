package vf.llama.database.access.single.persona.settings

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaSettingsDbFactory
import vf.llama.database.storable.persona.PersonaSettingsDbModel
import vf.llama.model.stored.persona.PersonaSettings

/**
  * Used for accessing individual persona settings
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaSettings 
	extends SingleRowModelAccess[PersonaSettings] with NonDeprecatedView[PersonaSettings] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaSettingsDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona settings
	  * @return An access point to that persona settings
	  */
	def apply(id: Int) = DbSinglePersonaSettings(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona settings.
	  * @return An access point to the persona settings that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniquePersonaSettingsAccess(mergeCondition(additionalCondition))
}

