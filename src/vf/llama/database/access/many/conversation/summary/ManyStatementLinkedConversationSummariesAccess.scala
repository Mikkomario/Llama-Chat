package vf.llama.database.access.many.conversation.summary

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.StatementLinkedConversationSummaryDbFactory
import vf.llama.database.storable.conversation.ConversationSummaryStatementLinkDbModel
import vf.llama.model.combined.conversation.StatementLinkedConversationSummary
import vf.llama.model.enumeration.SummaryStatementRole

object ManyStatementLinkedConversationSummariesAccess 
	extends ViewFactory[ManyStatementLinkedConversationSummariesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyStatementLinkedConversationSummariesAccess = 
		_ManyStatementLinkedConversationSummariesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyStatementLinkedConversationSummariesAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManyStatementLinkedConversationSummariesAccess
}

/**
  * A common trait for access points that return multiple statement linked conversation summaries at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyStatementLinkedConversationSummariesAccess 
	extends ManyConversationSummariesAccessLike[StatementLinkedConversationSummary, 
		ManyStatementLinkedConversationSummariesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * summary ids of the accessible conversation summary statement links
	  */
	def linkSummaryIds(implicit connection: Connection) = 
		pullColumn(linkModel.summaryId.column).map { v => v.getInt }
	
	/**
	  * statement ids of the accessible conversation summary statement links
	  */
	def linkStatementIds(implicit connection: Connection) = 
		pullColumn(linkModel.statementId.column).map { v => v.getInt }
	
	/**
	  * order indices of the accessible conversation summary statement links
	  */
	def linkOrderIndices(implicit connection: Connection) = 
		pullColumn(linkModel.orderIndex.column).map { v => v.getInt }
	
	/**
	  * roles of the accessible conversation summary statement links
	  */
	def linkRoles(implicit connection: Connection) = 
		pullColumn(linkModel.role.column).map { v => v.getInt }.flatMap(SummaryStatementRole.findForId)
	
	/**
	  * Model (factory) used for interacting the conversation summary statement links associated 
	  * with this statement linked conversation summary
	  */
	protected def linkModel = ConversationSummaryStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedConversationSummaryDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyStatementLinkedConversationSummariesAccess = 
		ManyStatementLinkedConversationSummariesAccess(condition)
}

