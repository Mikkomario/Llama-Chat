package vf.llama.model.factory.conversation

import vf.llama.model.factory.statement.StatementLinkFactoryWrapper

/**
  * Common trait for classes that implement SessionSummaryStatementLinkFactory by wrapping
  *  a SessionSummaryStatementLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait SessionSummaryStatementLinkFactoryWrapper[A <: SessionSummaryStatementLinkFactory[A], +Repr] 
	extends SessionSummaryStatementLinkFactory[Repr] with StatementLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withSessionId(sessionId: Int) = withParentId(sessionId)
}

