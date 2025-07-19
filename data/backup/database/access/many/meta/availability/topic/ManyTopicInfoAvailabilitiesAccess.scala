package vf.llama.database.access.many.meta.availability.topic

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.many.meta.availability.ManyMetaInfoAvailabilitiesAccessLike
import vf.llama.database.factory.meta.TopicInfoAvailabilityDbFactory
import vf.llama.database.storable.meta.TopicInfoAvailabilityDbModel
import vf.llama.model.stored.meta.TopicInfoAvailability

object ManyTopicInfoAvailabilitiesAccess extends ViewFactory[ManyTopicInfoAvailabilitiesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyTopicInfoAvailabilitiesAccess = 
		_ManyTopicInfoAvailabilitiesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyTopicInfoAvailabilitiesAccess(override val accessCondition: Option[Condition]) 
		extends ManyTopicInfoAvailabilitiesAccess
}

/**
  * A common trait for access points which target multiple topic info availabilities at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyTopicInfoAvailabilitiesAccess 
	extends ManyMetaInfoAvailabilitiesAccessLike[TopicInfoAvailability, ManyTopicInfoAvailabilitiesAccess] 
		with ManyRowModelAccess[TopicInfoAvailability]
{
	// COMPUTED	--------------------
	
	/**
	  * topic ids of the accessible topic info availabilities
	  */
	def topicIds(implicit connection: Connection) = contextIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicInfoAvailabilityDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = TopicInfoAvailabilityDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyTopicInfoAvailabilitiesAccess = 
		ManyTopicInfoAvailabilitiesAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param topicId topic id to target
	  * @return Copy of this access point that only includes topic info availabilities 
		with the specified topic id
	  */
	def inTopic(topicId: Int) = filter(model.topicId.column <=> topicId)
	
	/**
	  * @param topicIds Targeted topic ids
	  * 
		@return Copy of this access point that only includes topic info availabilities where topic id is within
	  *  the specified value set
	  */
	def inTopics(topicIds: IterableOnce[Int]) = filter(model.topicId.column.in(IntSet.from(topicIds)))
}

