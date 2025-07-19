package vf.llama.model.factory.conversation

import vf.llama.model.factory.statement.StatementLinkFactoryWrapper

/**
  * Common trait for classes that implement MessageStatementLinkFactory by wrapping
  *  a MessageStatementLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MessageStatementLinkFactoryWrapper[A <: MessageStatementLinkFactory[A], +Repr] 
	extends MessageStatementLinkFactory[Repr] with StatementLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withMessageId(messageId: Int) = withParentId(messageId)
}

