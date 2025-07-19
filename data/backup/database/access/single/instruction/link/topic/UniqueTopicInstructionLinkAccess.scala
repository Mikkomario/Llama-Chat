package vf.llama.database.access.single.instruction.link.topic

import utopia.vault.database.Connection
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.access.single.instruction.link.UniqueInstructionTargetingLinkAccessLike
import vf.llama.database.factory.instruction.TopicInstructionLinkDbFactory
import vf.llama.database.storable.instruction.TopicInstructionLinkDbModel
import vf.llama.model.stored.instruction.TopicInstructionLink

object UniqueTopicInstructionLinkAccess extends ViewFactory[UniqueTopicInstructionLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueTopicInstructionLinkAccess = 
		_UniqueTopicInstructionLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueTopicInstructionLinkAccess(override val accessCondition: Option[Condition]) 
		extends UniqueTopicInstructionLinkAccess
}

/**
  * A common trait for access points that return individual and distinct topic instruction links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueTopicInstructionLinkAccess 
	extends UniqueInstructionTargetingLinkAccessLike[TopicInstructionLink, UniqueTopicInstructionLinkAccess] 
		with NullDeprecatableView[UniqueTopicInstructionLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the topic where the linked instruction applies. 
	  * None if no topic instruction link (or value) was found.
	  */
	def topicId(implicit connection: Connection) = targetId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicInstructionLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = TopicInstructionLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueTopicInstructionLinkAccess = 
		UniqueTopicInstructionLinkAccess(condition)
}

