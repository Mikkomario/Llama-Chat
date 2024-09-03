package vf.llama.database.access.single.conversation.summary

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.StatementLinkedConversationSummaryDbFactory
import vf.llama.database.storable.conversation.{ConversationSummaryDbModel, ConversationSummaryStatementLinkDbModel}
import vf.llama.model.combined.conversation.StatementLinkedConversationSummary

/**
  * Used for accessing individual statement linked conversation summaries
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbStatementLinkedConversationSummary 
	extends SingleModelAccess[StatementLinkedConversationSummary] 
		with NonDeprecatedView[StatementLinkedConversationSummary] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = ConversationSummaryStatementLinkDbModel
	
	/**
	  * A database model (factory) used for interacting with linked summaries
	  */
	private def model = ConversationSummaryDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedConversationSummaryDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted statement linked conversation summary
	  * @return An access point to that statement linked conversation summary
	  */
	def apply(id: Int) = DbSingleStatementLinkedConversationSummary(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique statement linked conversation summaries.
	  * 
		@return An access point to the statement linked conversation summary that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueStatementLinkedConversationSummaryAccess(mergeCondition(additionalCondition))
}

