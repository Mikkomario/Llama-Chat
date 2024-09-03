package vf.llama.database.access.single.persona.settings

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaSettingsWithParametersDbFactory
import vf.llama.database.storable.persona.{PersonaParameterDbModel, PersonaSettingsDbModel}
import vf.llama.model.combined.persona.PersonaSettingsWithParameters

/**
  * Used for accessing individual persona settings with parameters
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaSettingsWithParameters 
	extends SingleModelAccess[PersonaSettingsWithParameters] 
		with NonDeprecatedView[PersonaSettingsWithParameters] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked parameter
	  */
	protected def parameterModel = PersonaParameterDbModel
	
	/**
	  * A database model (factory) used for interacting with linked settingses
	  */
	private def model = PersonaSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaSettingsWithParametersDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona settings with parameters
	  * @return An access point to that persona settings with parameters
	  */
	def apply(id: Int) = DbSinglePersonaSettingsWithParameters(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield unique persona settings 
	  * with parameters.
	  * @return An access point to the persona settings with parameters that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniquePersonaSettingsWithParametersAccess(mergeCondition(additionalCondition))
}

