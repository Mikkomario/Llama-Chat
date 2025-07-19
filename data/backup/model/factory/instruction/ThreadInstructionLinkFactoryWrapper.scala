package vf.llama.model.factory.instruction

/**
  * Common trait for classes that implement ThreadInstructionLinkFactory by wrapping
  *  a ThreadInstructionLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ThreadInstructionLinkFactoryWrapper[A <: ThreadInstructionLinkFactory[A], +Repr] 
	extends ThreadInstructionLinkFactory[Repr] with InstructionTargetingLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withThreadId(threadId: Int) = withTargetId(threadId)
}

