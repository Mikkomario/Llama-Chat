package vf.llama.database.access.many.instruction.link.topic

import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.access.many.instruction.link.ManyInstructionTargetingLinksAccessLike
import vf.llama.database.factory.instruction.TopicInstructionLinkDbFactory
import vf.llama.database.storable.instruction.TopicInstructionLinkDbModel
import vf.llama.model.stored.instruction.TopicInstructionLink

object ManyTopicInstructionLinksAccess extends ViewFactory[ManyTopicInstructionLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyTopicInstructionLinksAccess = 
		_ManyTopicInstructionLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyTopicInstructionLinksAccess(override val accessCondition: Option[Condition]) 
		extends ManyTopicInstructionLinksAccess
}

/**
  * A common trait for access points which target multiple topic instruction links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyTopicInstructionLinksAccess 
	extends ManyInstructionTargetingLinksAccessLike[TopicInstructionLink, ManyTopicInstructionLinksAccess] 
		with ChronoRowFactoryView[TopicInstructionLink, ManyTopicInstructionLinksAccess] 
		with NullDeprecatableView[ManyTopicInstructionLinksAccess] 
		with ManyRowModelAccess[TopicInstructionLink]
{
	// COMPUTED	--------------------
	
	/**
	  * topic ids of the accessible topic instruction links
	  */
	def topicIds(implicit connection: Connection) = targetIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicInstructionLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = TopicInstructionLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyTopicInstructionLinksAccess = 
		ManyTopicInstructionLinksAccess(condition)
}

