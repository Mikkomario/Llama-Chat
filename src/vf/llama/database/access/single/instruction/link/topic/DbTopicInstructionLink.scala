package vf.llama.database.access.single.instruction.link.topic

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.TopicInstructionLinkDbFactory
import vf.llama.database.storable.instruction.TopicInstructionLinkDbModel
import vf.llama.model.stored.instruction.TopicInstructionLink

/**
  * Used for accessing individual topic instruction links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbTopicInstructionLink 
	extends SingleRowModelAccess[TopicInstructionLink] with NonDeprecatedView[TopicInstructionLink] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = TopicInstructionLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TopicInstructionLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted topic instruction link
	  * @return An access point to that topic instruction link
	  */
	def apply(id: Int) = DbSingleTopicInstructionLink(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique topic instruction links.
	  * @return An access point to the topic instruction link that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueTopicInstructionLinkAccess(mergeCondition(additionalCondition))
}

