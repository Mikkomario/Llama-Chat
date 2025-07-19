package vf.llama.database.access.single.persona.settings

import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.StatementLinkedPersonaSettingsDbFactory
import vf.llama.database.storable.persona.PersonaStatementLinkDbModel
import vf.llama.model.combined.persona.StatementLinkedPersonaSettings

object UniqueStatementLinkedPersonaSettingsAccess 
	extends ViewFactory[UniqueStatementLinkedPersonaSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueStatementLinkedPersonaSettingsAccess = 
		_UniqueStatementLinkedPersonaSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueStatementLinkedPersonaSettingsAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniqueStatementLinkedPersonaSettingsAccess
}

/**
  * A common trait for access points that return distinct statement linked persona settings
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueStatementLinkedPersonaSettingsAccess 
	extends UniquePersonaSettingsAccessLike[StatementLinkedPersonaSettings, UniqueStatementLinkedPersonaSettingsAccess] 
		with NullDeprecatableView[UniqueStatementLinkedPersonaSettingsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked links
	  */
	protected def linkModel = PersonaStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedPersonaSettingsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueStatementLinkedPersonaSettingsAccess = 
		UniqueStatementLinkedPersonaSettingsAccess(condition)
}

