package vf.llama.model.combined.conversation

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.conversation.TopicFactoryWrapper
import vf.llama.model.partial.conversation.TopicData
import vf.llama.model.stored.conversation.Topic

/**
  * Common trait for combinations that add additional data to topics
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedTopic[+Repr] extends Extender[TopicData] with HasId[Int] with TopicFactoryWrapper[Topic, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped topic
	  */
	def topic: Topic
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this topic in the database
	  */
	override def id = topic.id
	
	override def wrapped = topic.data
	
	override protected def wrappedFactory = topic
}

