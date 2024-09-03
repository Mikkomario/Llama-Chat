package vf.llama.model.combined.conversation

import vf.llama.model.stored.conversation.{Message, MessageStatementLink}

object StatementLinkedMessage
{
	// OTHER	--------------------
	
	/**
	  * @param message message to wrap
	  * @param link link to attach to this message
	  * @return Combination of the specified message and link
	  */
	def apply(message: Message, link: Seq[MessageStatementLink]): StatementLinkedMessage = 
		_StatementLinkedMessage(message, link)
	
	
	// NESTED	--------------------
	
	/**
	  * @param message message to wrap
	  * @param link link to attach to this message
	  */
	private case class _StatementLinkedMessage(message: Message, link: Seq[MessageStatementLink]) 
		extends StatementLinkedMessage
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: Message) = copy(message = factory)
	}
}

/**
  * Includes links to the statements made within this message
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkedMessage extends CombinedMessage[StatementLinkedMessage]
{
	// ABSTRACT	--------------------
	
	/**
	  * Link that are attached to this message
	  */
	def link: Seq[MessageStatementLink]
}

