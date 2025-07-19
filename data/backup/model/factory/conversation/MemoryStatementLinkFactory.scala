package vf.llama.model.factory.conversation

import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for memory statement link-related factories which allow construction 
	with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MemoryStatementLinkFactory[+A] extends StatementLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param memoryId New memory id to assign
	  * @return Copy of this item with the specified memory id
	  */
	def withMemoryId(memoryId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withParentId(parentId: Int) = withMemoryId(parentId)
}

