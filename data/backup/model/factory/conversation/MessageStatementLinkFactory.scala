package vf.llama.model.factory.conversation

import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for message statement link-related factories which allow construction 
	with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MessageStatementLinkFactory[+A] extends StatementLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param messageId New message id to assign
	  * @return Copy of this item with the specified message id
	  */
	def withMessageId(messageId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withParentId(parentId: Int) = withMessageId(parentId)
}

