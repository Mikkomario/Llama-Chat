package vf.llama.database.access.single.meta.availability.topic

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.TopicInfoAvailabilityDbFactory
import vf.llama.database.storable.meta.TopicInfoAvailabilityDbModel
import vf.llama.model.stored.meta.TopicInfoAvailability

/**
  * Used for accessing individual topic info availabilities
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbTopicInfoAvailability 
	extends SingleRowModelAccess[TopicInfoAvailability] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = TopicInfoAvailabilityDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicInfoAvailabilityDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted topic info availability
	  * @return An access point to that topic info availability
	  */
	def apply(id: Int) = DbSingleTopicInfoAvailability(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique topic info availabilities.
	  * @return An access point to the topic info availability that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueTopicInfoAvailabilityAccess(condition)
}

