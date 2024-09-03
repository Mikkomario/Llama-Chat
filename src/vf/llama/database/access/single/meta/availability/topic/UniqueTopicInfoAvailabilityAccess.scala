package vf.llama.database.access.single.meta.availability.topic

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.meta.availability.UniqueMetaInfoAvailabilityAccessLike
import vf.llama.database.factory.meta.TopicInfoAvailabilityDbFactory
import vf.llama.database.storable.meta.TopicInfoAvailabilityDbModel
import vf.llama.model.stored.meta.TopicInfoAvailability

object UniqueTopicInfoAvailabilityAccess extends ViewFactory[UniqueTopicInfoAvailabilityAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueTopicInfoAvailabilityAccess = 
		_UniqueTopicInfoAvailabilityAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueTopicInfoAvailabilityAccess(override val accessCondition: Option[Condition]) 
		extends UniqueTopicInfoAvailabilityAccess
}

/**
  * A common trait for access points that return individual and distinct topic info availabilities.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueTopicInfoAvailabilityAccess 
	extends UniqueMetaInfoAvailabilityAccessLike[TopicInfoAvailability, UniqueTopicInfoAvailabilityAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the topic where the linked information is made available. 
	  * None if no topic info availability (or value) was found.
	  */
	def topicId(implicit connection: Connection) = contextId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicInfoAvailabilityDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = TopicInfoAvailabilityDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueTopicInfoAvailabilityAccess = 
		UniqueTopicInfoAvailabilityAccess(condition)
}

