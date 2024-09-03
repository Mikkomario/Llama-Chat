package vf.llama.database.access.single.meta.availability.persona

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.meta.availability.UniqueMetaInfoAvailabilityAccessLike
import vf.llama.database.factory.meta.PersonaInfoAvailabilityDbFactory
import vf.llama.database.storable.meta.PersonaInfoAvailabilityDbModel
import vf.llama.model.stored.meta.PersonaInfoAvailability

object UniquePersonaInfoAvailabilityAccess extends ViewFactory[UniquePersonaInfoAvailabilityAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaInfoAvailabilityAccess = 
		_UniquePersonaInfoAvailabilityAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaInfoAvailabilityAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaInfoAvailabilityAccess
}

/**
  * A common trait for access points that return individual and distinct persona info availabilities.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaInfoAvailabilityAccess 
	extends UniqueMetaInfoAvailabilityAccessLike[PersonaInfoAvailability, UniquePersonaInfoAvailabilityAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the persona to which the linked information is made available. 
	  * None if no persona info availability (or value) was found.
	  */
	def personaId(implicit connection: Connection) = contextId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaInfoAvailabilityDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = PersonaInfoAvailabilityDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaInfoAvailabilityAccess = 
		UniquePersonaInfoAvailabilityAccess(condition)
}

