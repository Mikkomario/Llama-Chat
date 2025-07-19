package vf.llama.model.partial.instruction

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.time.Now
import vf.llama.model.factory.instruction.ThreadInstructionLinkFactory

import java.time.Instant

object ThreadInstructionLinkData extends FromModelFactoryWithSchema[ThreadInstructionLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("threadId", IntType, Vector("targetId", "target_id", 
			"thread_id")), PropertyDeclaration("instructionId", IntType, Single("instruction_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("removedAfter", InstantType, Single("removed_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		ThreadInstructionLinkData(valid("threadId").getInt, valid("instructionId").getInt, 
			valid("created").getInstant, valid("removedAfter").instant)
}

/**
  * Links an instruction to a thread where it applies
  * @param threadId Id of the thread where the linked instruction applies
  * @param instructionId Id of the instruction applied to the linked entity
  * @param created Time when this instruction targeting link was added to the database
  * @param removedAfter Time after which this link was removed
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ThreadInstructionLinkData(threadId: Int, instructionId: Int, created: Instant = Now, 
	removedAfter: Option[Instant] = None) 
	extends ThreadInstructionLinkFactory[ThreadInstructionLinkData] with InstructionTargetingLinkData 
		with InstructionTargetingLinkDataLike[ThreadInstructionLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def targetId = threadId
	
	override def copyInstructionTargetingLink(targetId: Int, instructionId: Int, created: Instant, 
		removedAfter: Option[Instant]) = 
		copy(threadId = targetId, instructionId = instructionId, created = created, 
			removedAfter = removedAfter)
	
	override def withThreadId(threadId: Int) = copy(threadId = threadId)
}

