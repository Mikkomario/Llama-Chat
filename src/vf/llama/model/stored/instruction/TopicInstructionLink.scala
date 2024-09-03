package vf.llama.model.stored.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.instruction.link.topic.DbSingleTopicInstructionLink
import vf.llama.model.factory.instruction.TopicInstructionLinkFactoryWrapper
import vf.llama.model.partial.instruction.{InstructionTargetingLinkData, TopicInstructionLinkData}

object TopicInstructionLink extends StoredFromModelFactory[TopicInstructionLinkData, TopicInstructionLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = TopicInstructionLinkData
	
	override protected def complete(model: AnyModel, data: TopicInstructionLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a topic instruction link that has already been stored in the database
  * @param id id of this topic instruction link in the database
  * @param data Wrapped topic instruction link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicInstructionLink(id: Int, data: TopicInstructionLinkData) 
	extends TopicInstructionLinkFactoryWrapper[TopicInstructionLinkData, TopicInstructionLink] 
		with InstructionTargetingLinkData 
		with StoredInstructionTargetingLinkLike[TopicInstructionLinkData, TopicInstructionLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this topic instruction link in the database
	  */
	def access = DbSingleTopicInstructionLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: TopicInstructionLinkData) = copy(data = data)
}

