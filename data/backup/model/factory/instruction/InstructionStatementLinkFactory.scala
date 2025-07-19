package vf.llama.model.factory.instruction

import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for instruction statement link-related factories which allow construction 
  * with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionStatementLinkFactory[+A] extends StatementLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param instructionVersionId New instruction version id to assign
	  * @return Copy of this item with the specified instruction version id
	  */
	def withInstructionVersionId(instructionVersionId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withParentId(parentId: Int) = withInstructionVersionId(parentId)
}

