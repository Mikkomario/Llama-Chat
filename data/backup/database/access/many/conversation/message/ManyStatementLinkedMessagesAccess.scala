package vf.llama.database.access.many.conversation.message

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.StatementLinkedMessageDbFactory
import vf.llama.database.storable.conversation.MessageStatementLinkDbModel
import vf.llama.model.combined.conversation.StatementLinkedMessage

object ManyStatementLinkedMessagesAccess extends ViewFactory[ManyStatementLinkedMessagesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyStatementLinkedMessagesAccess = 
		_ManyStatementLinkedMessagesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyStatementLinkedMessagesAccess(override val accessCondition: Option[Condition]) 
		extends ManyStatementLinkedMessagesAccess
}

/**
  * A common trait for access points that return multiple statement linked messages at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyStatementLinkedMessagesAccess 
	extends ManyMessagesAccessLike[StatementLinkedMessage, ManyStatementLinkedMessagesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * message ids of the accessible message statement links
	  */
	def linkMessageIds(implicit connection: Connection) = 
		pullColumn(linkModel.messageId.column).map { v => v.getInt }
	
	/**
	  * statement ids of the accessible message statement links
	  */
	def linkStatementIds(implicit connection: Connection) = 
		pullColumn(linkModel.statementId.column).map { v => v.getInt }
	
	/**
	  * order indices of the accessible message statement links
	  */
	def linkOrderIndices(implicit connection: Connection) = 
		pullColumn(linkModel.orderIndex.column).map { v => v.getInt }
	
	/**
	  * Model (factory) used for interacting the message statement links associated 
		with this statement linked message
	  */
	protected def linkModel = MessageStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedMessageDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyStatementLinkedMessagesAccess = 
		ManyStatementLinkedMessagesAccess(condition)
}

