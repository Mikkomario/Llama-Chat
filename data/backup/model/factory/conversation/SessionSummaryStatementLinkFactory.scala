package vf.llama.model.factory.conversation

import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for session summary statement link-related factories which allow construction 
  * with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait SessionSummaryStatementLinkFactory[+A] extends StatementLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param sessionId New session id to assign
	  * @return Copy of this item with the specified session id
	  */
	def withSessionId(sessionId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withParentId(parentId: Int) = withSessionId(parentId)
}

