package vf.llama.model.factory.instruction

/**
  * Common trait for thread instruction link-related factories which allow construction 
	with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ThreadInstructionLinkFactory[+A] extends InstructionTargetingLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param threadId New thread id to assign
	  * @return Copy of this item with the specified thread id
	  */
	def withThreadId(threadId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withTargetId(targetId: Int) = withThreadId(targetId)
}

