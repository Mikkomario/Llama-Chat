package vf.llama.database.access.single.conversation.summary

import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.StatementLinkedConversationSummaryDbFactory
import vf.llama.database.storable.conversation.ConversationSummaryStatementLinkDbModel
import vf.llama.model.combined.conversation.StatementLinkedConversationSummary

object UniqueStatementLinkedConversationSummaryAccess 
	extends ViewFactory[UniqueStatementLinkedConversationSummaryAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueStatementLinkedConversationSummaryAccess = 
		_UniqueStatementLinkedConversationSummaryAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueStatementLinkedConversationSummaryAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniqueStatementLinkedConversationSummaryAccess
}

/**
  * A common trait for access points that return distinct statement linked conversation summaries
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueStatementLinkedConversationSummaryAccess 
	extends UniqueConversationSummaryAccessLike[StatementLinkedConversationSummary, UniqueStatementLinkedConversationSummaryAccess] 
		with NullDeprecatableView[UniqueStatementLinkedConversationSummaryAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = ConversationSummaryStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedConversationSummaryDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueStatementLinkedConversationSummaryAccess = 
		UniqueStatementLinkedConversationSummaryAccess(condition)
}

