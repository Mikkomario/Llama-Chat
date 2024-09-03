package vf.llama.model.factory.conversation

import vf.llama.model.enumeration.SummaryStatementRole
import vf.llama.model.factory.statement.StatementLinkFactoryWrapper

/**
  * Common trait for classes that implement ConversationSummaryStatementLinkFactory by wrapping
  *  a ConversationSummaryStatementLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ConversationSummaryStatementLinkFactoryWrapper[A <: ConversationSummaryStatementLinkFactory[A], +Repr] 
	extends ConversationSummaryStatementLinkFactory[Repr] with StatementLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withRole(role: SummaryStatementRole) = mapWrapped { _.withRole(role) }
	
	override def withSummaryId(summaryId: Int) = withParentId(summaryId)
}

