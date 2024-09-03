package vf.llama.database.access.single.conversation.message.link.statement

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.statement.UniqueStatementLinkAccessLike
import vf.llama.database.factory.conversation.MessageStatementLinkDbFactory
import vf.llama.database.storable.conversation.MessageStatementLinkDbModel
import vf.llama.model.stored.conversation.MessageStatementLink

object UniqueMessageStatementLinkAccess extends ViewFactory[UniqueMessageStatementLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueMessageStatementLinkAccess = 
		_UniqueMessageStatementLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueMessageStatementLinkAccess(override val accessCondition: Option[Condition]) 
		extends UniqueMessageStatementLinkAccess
}

/**
  * A common trait for access points that return individual and distinct message statement links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueMessageStatementLinkAccess 
	extends UniqueStatementLinkAccessLike[MessageStatementLink, UniqueMessageStatementLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the message where the linked statement appears. 
	  * None if no message statement link (or value) was found.
	  */
	def messageId(implicit connection: Connection) = parentId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MessageStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = MessageStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueMessageStatementLinkAccess = 
		UniqueMessageStatementLinkAccess(condition)
}

