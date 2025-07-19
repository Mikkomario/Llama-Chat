package vf.llama.database.access.single.conversation.topic

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.TopicDbFactory
import vf.llama.model.stored.conversation.Topic

object UniqueTopicAccess extends ViewFactory[UniqueTopicAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueTopicAccess = _UniqueTopicAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueTopicAccess(override val accessCondition: Option[Condition]) 
		extends UniqueTopicAccess
}

/**
  * A common trait for access points that return individual and distinct topics.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueTopicAccess 
	extends UniqueTopicAccessLike[Topic, UniqueTopicAccess] 
		with SingleChronoRowModelAccess[Topic, UniqueTopicAccess] with NullDeprecatableView[UniqueTopicAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = TopicDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueTopicAccess = UniqueTopicAccess(condition)
}

