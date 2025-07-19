package vf.llama.database.access.single.persona.link.statement

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.statement.UniqueStatementLinkAccessLike
import vf.llama.database.factory.persona.PersonaStatementLinkDbFactory
import vf.llama.database.storable.persona.PersonaStatementLinkDbModel
import vf.llama.model.enumeration.PersonaDescriptionType
import vf.llama.model.stored.persona.PersonaStatementLink

object UniquePersonaStatementLinkAccess extends ViewFactory[UniquePersonaStatementLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaStatementLinkAccess = 
		_UniquePersonaStatementLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaStatementLinkAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaStatementLinkAccess
}

/**
  * A common trait for access points that return individual and distinct persona statement links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaStatementLinkAccess 
	extends UniqueStatementLinkAccessLike[PersonaStatementLink, UniquePersonaStatementLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the settings to which this link is tied to. 
	  * None if no persona statement link (or value) was found.
	  */
	def personaSettingsId(implicit connection: Connection) = parentId
	
	/**
	  * Context in which the linked statement appears. 
	  * None if no persona statement link (or value) was found.
	  */
	def descriptionType(implicit connection: Connection) = 
		pullColumn(model.descriptionType.column).int.flatMap(PersonaDescriptionType.findForId)
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = PersonaStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaStatementLinkAccess = 
		UniquePersonaStatementLinkAccess(condition)
}

