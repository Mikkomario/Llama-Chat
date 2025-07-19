package vf.llama.model.factory.conversation

import vf.llama.model.enumeration.SummaryStatementRole
import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for conversation summary statement link-related factories which allow construction 
  * with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ConversationSummaryStatementLinkFactory[+A] extends StatementLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param role New role to assign
	  * @return Copy of this item with the specified role
	  */
	def withRole(role: SummaryStatementRole): A
	
	/**
	  * @param summaryId New summary id to assign
	  * @return Copy of this item with the specified summary id
	  */
	def withSummaryId(summaryId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withParentId(parentId: Int) = withSummaryId(parentId)
}

