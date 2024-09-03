package vf.llama.database.access.many.persona.settings

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.StatementLinkedPersonaSettingsDbFactory
import vf.llama.database.storable.persona.PersonaStatementLinkDbModel
import vf.llama.model.combined.persona.StatementLinkedPersonaSettings
import vf.llama.model.enumeration.PersonaDescriptionType

object ManyStatementLinkedPersonaSettingsAccess extends ViewFactory[ManyStatementLinkedPersonaSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyStatementLinkedPersonaSettingsAccess = 
		_ManyStatementLinkedPersonaSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyStatementLinkedPersonaSettingsAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManyStatementLinkedPersonaSettingsAccess
}

/**
  * A common trait for access points that return multiple statement linked persona settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyStatementLinkedPersonaSettingsAccess 
	extends ManyPersonaSettingsAccessLike[StatementLinkedPersonaSettings, 
		ManyStatementLinkedPersonaSettingsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * persona settings ids of the accessible persona statement links
	  */
	def linkPersonaSettingsIds(implicit connection: Connection) = 
		pullColumn(linkModel.personaSettingsId.column).map { v => v.getInt }
	
	/**
	  * statement ids of the accessible persona statement links
	  */
	def linkStatementIds(implicit connection: Connection) = 
		pullColumn(linkModel.statementId.column).map { v => v.getInt }
	
	/**
	  * order indices of the accessible persona statement links
	  */
	def linkOrderIndices(implicit connection: Connection) = 
		pullColumn(linkModel.orderIndex.column).map { v => v.getInt }
	
	/**
	  * description types of the accessible persona statement links
	  */
	def linkDescriptionTypes(implicit connection: Connection) = 
		pullColumn(linkModel.descriptionType.column)
			.map { v => v.getInt }.flatMap(PersonaDescriptionType.findForId)
	
	/**
	  * Model (factory) used for interacting the persona statement links associated 
	  * with this statement linked persona settings
	  */
	protected def linkModel = PersonaStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedPersonaSettingsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyStatementLinkedPersonaSettingsAccess = 
		ManyStatementLinkedPersonaSettingsAccess(condition)
}

