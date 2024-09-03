package vf.llama.model.factory.instruction

import vf.llama.model.factory.statement.StatementLinkFactoryWrapper

/**
  * Common trait for classes that implement InstructionStatementLinkFactory by wrapping
  *  a InstructionStatementLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionStatementLinkFactoryWrapper[A <: InstructionStatementLinkFactory[A], +Repr] 
	extends InstructionStatementLinkFactory[Repr] with StatementLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withInstructionVersionId(instructionVersionId: Int) = withParentId(instructionVersionId)
}

