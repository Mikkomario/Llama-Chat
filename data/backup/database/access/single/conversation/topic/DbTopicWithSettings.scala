package vf.llama.database.access.single.conversation.topic

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.TopicWithSettingsDbFactory
import vf.llama.database.storable.conversation.{TopicDbModel, TopicSettingsDbModel}
import vf.llama.model.combined.conversation.TopicWithSettings

/**
  * Used for accessing individual topics with settings
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbTopicWithSettings 
	extends SingleRowModelAccess[TopicWithSettings] with NonDeprecatedView[TopicWithSettings] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked settings
	  */
	protected def settingsModel = TopicSettingsDbModel
	
	/**
	  * A database model (factory) used for interacting with linked topics
	  */
	private def model = TopicDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicWithSettingsDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted topic with settings
	  * @return An access point to that topic with settings
	  */
	def apply(id: Int) = DbSingleTopicWithSettings(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield unique topics 
	  * with settings.
	  * @return An access point to the topic with settings that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueTopicWithSettingsAccess(mergeCondition(additionalCondition))
}

