package vf.llama.database.access.single.conversation.topic

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.TopicDbFactory
import vf.llama.database.storable.conversation.TopicDbModel
import vf.llama.model.stored.conversation.Topic

/**
  * Used for accessing individual topics
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbTopic extends SingleRowModelAccess[Topic] with NonDeprecatedView[Topic] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = TopicDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted topic
	  * @return An access point to that topic
	  */
	def apply(id: Int) = DbSingleTopic(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique topics.
	  * @return An access point to the topic that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueTopicAccess(mergeCondition(additionalCondition))
}

