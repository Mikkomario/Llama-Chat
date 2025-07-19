package vf.llama.database.access.single.conversation.summary.link.statement

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.statement.UniqueStatementLinkAccessLike
import vf.llama.database.factory.conversation.ConversationSummaryStatementLinkDbFactory
import vf.llama.database.storable.conversation.ConversationSummaryStatementLinkDbModel
import vf.llama.model.enumeration.SummaryStatementRole
import vf.llama.model.stored.conversation.ConversationSummaryStatementLink

object UniqueConversationSummaryStatementLinkAccess 
	extends ViewFactory[UniqueConversationSummaryStatementLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueConversationSummaryStatementLinkAccess = 
		_UniqueConversationSummaryStatementLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueConversationSummaryStatementLinkAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniqueConversationSummaryStatementLinkAccess
}

/**
  * A common trait for access points that return individual and distinct conversation summary statement links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueConversationSummaryStatementLinkAccess 
	extends UniqueStatementLinkAccessLike[ConversationSummaryStatementLink, 
		UniqueConversationSummaryStatementLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the linked conversation summary. 
	  * None if no conversation summary statement link (or value) was found.
	  */
	def summaryId(implicit connection: Connection) = parentId
	
	/**
	  * Role of the linked statement in the linked summary. 
	  * None if no conversation summary statement link (or value) was found.
	  */
	def role(implicit connection: Connection) = 
		pullColumn(model.role.column).int.flatMap(SummaryStatementRole.findForId)
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationSummaryStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = ConversationSummaryStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueConversationSummaryStatementLinkAccess = 
		UniqueConversationSummaryStatementLinkAccess(condition)
}

