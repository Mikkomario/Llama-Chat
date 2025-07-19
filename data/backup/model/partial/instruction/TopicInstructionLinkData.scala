package vf.llama.model.partial.instruction

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.time.Now
import vf.llama.model.factory.instruction.TopicInstructionLinkFactory

import java.time.Instant

object TopicInstructionLinkData extends FromModelFactoryWithSchema[TopicInstructionLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("topicId", IntType, Vector("targetId", "target_id", 
			"topic_id")), PropertyDeclaration("instructionId", IntType, Single("instruction_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("removedAfter", InstantType, Single("removed_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		TopicInstructionLinkData(valid("topicId").getInt, valid("instructionId").getInt, 
			valid("created").getInstant, valid("removedAfter").instant)
}

/**
  * Links an instruction to a topic where it applies
  * @param topicId Id of the topic where the linked instruction applies
  * @param instructionId Id of the instruction applied to the linked entity
  * @param created Time when this instruction targeting link was added to the database
  * @param removedAfter Time after which this link was removed
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicInstructionLinkData(topicId: Int, instructionId: Int, created: Instant = Now, 
	removedAfter: Option[Instant] = None) 
	extends TopicInstructionLinkFactory[TopicInstructionLinkData] with InstructionTargetingLinkData 
		with InstructionTargetingLinkDataLike[TopicInstructionLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def targetId = topicId
	
	override def copyInstructionTargetingLink(targetId: Int, instructionId: Int, created: Instant, 
		removedAfter: Option[Instant]) = 
		copy(topicId = targetId, instructionId = instructionId, created = created, 
			removedAfter = removedAfter)
	
	override def withTopicId(topicId: Int) = copy(topicId = topicId)
}

