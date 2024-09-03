package vf.llama.database.factory.conversation

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import vf.llama.model.combined.conversation.StatementLinkedMessage
import vf.llama.model.stored.conversation.{Message, MessageStatementLink}

/**
  * Used for reading statement linked messages from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object StatementLinkedMessageDbFactory 
	extends MultiCombiningFactory[StatementLinkedMessage, Message, MessageStatementLink]
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = MessageStatementLinkDbFactory
	
	override def isAlwaysLinked = true
	
	override def parentFactory = MessageDbFactory
	
	/**
	  * @param message message to wrap
	  * @param link link to attach to this message
	  */
	override def apply(message: Message, link: Seq[MessageStatementLink]) = StatementLinkedMessage(message, 
		link)
}

