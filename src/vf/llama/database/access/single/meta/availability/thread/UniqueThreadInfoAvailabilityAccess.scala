package vf.llama.database.access.single.meta.availability.thread

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.meta.availability.UniqueMetaInfoAvailabilityAccessLike
import vf.llama.database.factory.meta.ThreadInfoAvailabilityDbFactory
import vf.llama.database.storable.meta.ThreadInfoAvailabilityDbModel
import vf.llama.model.stored.meta.ThreadInfoAvailability

object UniqueThreadInfoAvailabilityAccess extends ViewFactory[UniqueThreadInfoAvailabilityAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueThreadInfoAvailabilityAccess = 
		_UniqueThreadInfoAvailabilityAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueThreadInfoAvailabilityAccess(override val accessCondition: Option[Condition]) 
		extends UniqueThreadInfoAvailabilityAccess
}

/**
  * A common trait for access points that return individual and distinct thread info availabilities.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueThreadInfoAvailabilityAccess 
	extends UniqueMetaInfoAvailabilityAccessLike[ThreadInfoAvailability, UniqueThreadInfoAvailabilityAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the thread where the linked information is made available. 
	  * None if no thread info availability (or value) was found.
	  */
	def threadId(implicit connection: Connection) = contextId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ThreadInfoAvailabilityDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = ThreadInfoAvailabilityDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueThreadInfoAvailabilityAccess = 
		UniqueThreadInfoAvailabilityAccess(condition)
}

