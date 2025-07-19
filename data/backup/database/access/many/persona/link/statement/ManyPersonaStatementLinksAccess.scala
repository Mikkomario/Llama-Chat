package vf.llama.database.access.many.persona.link.statement

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.many.statement.ManyStatementLinksAccessLike
import vf.llama.database.factory.persona.PersonaStatementLinkDbFactory
import vf.llama.database.storable.persona.PersonaStatementLinkDbModel
import vf.llama.model.enumeration.PersonaDescriptionType
import vf.llama.model.stored.persona.PersonaStatementLink

object ManyPersonaStatementLinksAccess extends ViewFactory[ManyPersonaStatementLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaStatementLinksAccess = 
		_ManyPersonaStatementLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaStatementLinksAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaStatementLinksAccess
}

/**
  * A common trait for access points which target multiple persona statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaStatementLinksAccess 
	extends ManyStatementLinksAccessLike[PersonaStatementLink, ManyPersonaStatementLinksAccess] 
		with ManyRowModelAccess[PersonaStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * persona settings ids of the accessible persona statement links
	  */
	def personaSettingsIds(implicit connection: Connection) = parentIds
	
	/**
	  * description types of the accessible persona statement links
	  */
	def descriptionTypes(implicit connection: Connection) = 
		pullColumn(model.descriptionType.column)
			.map { v => v.getInt }.flatMap(PersonaDescriptionType.findForId)
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = PersonaStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonaStatementLinksAccess = 
		ManyPersonaStatementLinksAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param personaSettingsId persona settings id to target
	  * @return Copy of this access point that only includes persona statement links 
	  * with the specified persona settings id
	  */
	def inPersonaSettings(personaSettingsId: Int) = filter(model
		.personaSettingsId.column <=> personaSettingsId)
	
	/**
	  * @param personaSettingsIds Targeted persona settings ids
	  * 
		@return Copy of this access point that only includes persona statement links where persona settings id is
	  *  within the specified value set
	  */
	def inPersonaSettings(personaSettingsIds: IterableOnce[Int]) = 
		filter(model.personaSettingsId.column.in(IntSet.from(personaSettingsIds)))
}

