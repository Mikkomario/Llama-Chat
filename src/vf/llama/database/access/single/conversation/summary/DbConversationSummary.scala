package vf.llama.database.access.single.conversation.summary

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationSummaryDbFactory
import vf.llama.database.storable.conversation.ConversationSummaryDbModel
import vf.llama.model.stored.conversation.ConversationSummary

/**
  * Used for accessing individual conversation summaries
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbConversationSummary 
	extends SingleRowModelAccess[ConversationSummary] with NonDeprecatedView[ConversationSummary] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = ConversationSummaryDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationSummaryDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted conversation summary
	  * @return An access point to that conversation summary
	  */
	def apply(id: Int) = DbSingleConversationSummary(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique conversation summaries.
	  * @return An access point to the conversation summary that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueConversationSummaryAccess(mergeCondition(additionalCondition))
}

