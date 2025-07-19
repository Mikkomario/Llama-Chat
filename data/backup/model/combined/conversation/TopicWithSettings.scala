package vf.llama.model.combined.conversation

import vf.llama.model.stored.conversation.{Topic, TopicSettings}

object TopicWithSettings
{
	// OTHER	--------------------
	
	/**
	  * @param topic topic to wrap
	  * @param settings settings to attach to this topic
	  * @return Combination of the specified topic and settings
	  */
	def apply(topic: Topic, settings: TopicSettings): TopicWithSettings = _TopicWithSettings(topic, settings)
	
	
	// NESTED	--------------------
	
	/**
	  * @param topic topic to wrap
	  * @param settings settings to attach to this topic
	  */
	private case class _TopicWithSettings(topic: Topic, settings: TopicSettings) extends TopicWithSettings
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: Topic) = copy(topic = factory)
	}
}

/**
  * Includes settings with a topic instance
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait TopicWithSettings extends CombinedTopic[TopicWithSettings]
{
	// ABSTRACT	--------------------
	
	/**
	  * The settings that is attached to this topic
	  */
	def settings: TopicSettings
}

