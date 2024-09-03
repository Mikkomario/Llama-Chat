package vf.llama.model.stored.instruction

import utopia.vault.model.template.{FromIdFactory, Stored}
import vf.llama.model.factory.instruction.InstructionTargetingLinkFactoryWrapper
import vf.llama.model.partial.instruction.InstructionTargetingLinkDataLike

import java.time.Instant

/**
  * Common trait for instruction targeting links which have been stored in the database
  * @tparam Data Type of the wrapped data
  * @tparam Repr Implementing type
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StoredInstructionTargetingLinkLike[Data <: InstructionTargetingLinkDataLike[Data], +Repr] 
	extends Stored[Data, Int] with FromIdFactory[Int, Repr] 
		with InstructionTargetingLinkFactoryWrapper[Data, Repr] with InstructionTargetingLinkDataLike[Repr]
{
	// IMPLEMENTED	--------------------
	
	override def created = data.created
	
	override def instructionId = data.instructionId
	
	override def removedAfter = data.removedAfter
	
	override def targetId = data.targetId
	
	override protected def wrappedFactory = data
	
	override def copyInstructionTargetingLink(targetId: Int, instructionId: Int, created: Instant, 
		removedAfter: Option[Instant]) = 
		wrap(data.copyInstructionTargetingLink(targetId, instructionId, created, removedAfter))
}

