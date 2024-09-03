package vf.llama.database.access.single.conversation.topic.settings

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.TopicSettingsDbFactory
import vf.llama.database.storable.conversation.TopicSettingsDbModel
import vf.llama.model.stored.conversation.TopicSettings

/**
  * Used for accessing individual topic settings
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbTopicSettings 
	extends SingleRowModelAccess[TopicSettings] with NonDeprecatedView[TopicSettings] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = TopicSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicSettingsDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted topic settings
	  * @return An access point to that topic settings
	  */
	def apply(id: Int) = DbSingleTopicSettings(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique topic settings.
	  * @return An access point to the topic settings that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueTopicSettingsAccess(mergeCondition(additionalCondition))
}

