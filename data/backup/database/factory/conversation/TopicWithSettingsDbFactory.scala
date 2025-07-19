package vf.llama.database.factory.conversation

import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.linked.CombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.conversation.TopicWithSettings
import vf.llama.model.stored.conversation.{Topic, TopicSettings}

/**
  * Used for reading topics with settings from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object TopicWithSettingsDbFactory 
	extends CombiningFactory[TopicWithSettings, Topic, TopicSettings] 
		with FromTimelineRowFactory[TopicWithSettings] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = TopicSettingsDbFactory
	
	override def nonDeprecatedCondition = 
		parentFactory.nonDeprecatedCondition && childFactory.nonDeprecatedCondition
	
	override def parentFactory = TopicDbFactory
	
	override def timestamp = parentFactory.timestamp
	
	/**
	  * @param topic topic to wrap
	  * @param settings settings to attach to this topic
	  */
	override def apply(topic: Topic, settings: TopicSettings) = TopicWithSettings(topic, settings)
}

