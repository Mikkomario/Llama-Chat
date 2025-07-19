package vf.llama.database.access.single.persona.settings

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.StatementLinkedPersonaSettingsDbFactory
import vf.llama.database.storable.persona.{PersonaSettingsDbModel, PersonaStatementLinkDbModel}
import vf.llama.model.combined.persona.StatementLinkedPersonaSettings

/**
  * Used for accessing individual statement linked persona settings
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbStatementLinkedPersonaSettings 
	extends SingleModelAccess[StatementLinkedPersonaSettings] 
		with NonDeprecatedView[StatementLinkedPersonaSettings] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked links
	  */
	protected def linkModel = PersonaStatementLinkDbModel
	
	/**
	  * A database model (factory) used for interacting with linked settings
	  */
	private def model = PersonaSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedPersonaSettingsDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted statement linked persona settings
	  * @return An access point to that statement linked persona settings
	  */
	def apply(id: Int) = DbSingleStatementLinkedPersonaSettings(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique statement linked persona settings.
	  * 
		@return An access point to the statement linked persona settings that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueStatementLinkedPersonaSettingsAccess(mergeCondition(additionalCondition))
}

