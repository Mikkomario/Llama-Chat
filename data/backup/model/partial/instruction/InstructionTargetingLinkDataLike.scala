package vf.llama.model.partial.instruction

import vf.llama.model.factory.instruction.InstructionTargetingLinkFactory

import java.time.Instant

/**
  * Common trait for classes which provide read and copy access to instruction targeting link properties
  * @tparam Repr Implementing data class or data wrapper class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkDataLike[+Repr] 
	extends HasInstructionTargetingLinkProps with InstructionTargetingLinkFactory[Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Builds a modified copy of this instruction targeting link
	  * @param targetId New target id to assign. Default = current value.
	  * @param instructionId New instruction id to assign. Default = current value.
	  * @param created New created to assign. Default = current value.
	  * @param removedAfter New removed after to assign. Default = current value.
	  * @return A copy of this instruction targeting link with the specified properties
	  */
	def copyInstructionTargetingLink(targetId: Int = targetId, instructionId: Int = instructionId, 
		created: Instant = created, removedAfter: Option[Instant] = removedAfter): Repr
	
	
	// IMPLEMENTED	--------------------
	
	override def withCreated(created: Instant) = copyInstructionTargetingLink(created = created)
	
	override def withInstructionId(instructionId: Int) = 
		copyInstructionTargetingLink(instructionId = instructionId)
	
	override def withRemovedAfter(removedAfter: Instant) = 
		copyInstructionTargetingLink(removedAfter = Some(removedAfter))
	
	override def withTargetId(targetId: Int) = copyInstructionTargetingLink(targetId = targetId)
}

