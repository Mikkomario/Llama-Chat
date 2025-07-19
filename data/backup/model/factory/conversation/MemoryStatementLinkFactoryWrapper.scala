package vf.llama.model.factory.conversation

import vf.llama.model.factory.statement.StatementLinkFactoryWrapper

/**
  * Common trait for classes that implement MemoryStatementLinkFactory by wrapping
  *  a MemoryStatementLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MemoryStatementLinkFactoryWrapper[A <: MemoryStatementLinkFactory[A], +Repr] 
	extends MemoryStatementLinkFactory[Repr] with StatementLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withMemoryId(memoryId: Int) = withParentId(memoryId)
}

