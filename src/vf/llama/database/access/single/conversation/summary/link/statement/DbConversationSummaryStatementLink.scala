package vf.llama.database.access.single.conversation.summary.link.statement

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationSummaryStatementLinkDbFactory
import vf.llama.database.storable.conversation.ConversationSummaryStatementLinkDbModel
import vf.llama.model.stored.conversation.ConversationSummaryStatementLink

/**
  * Used for accessing individual conversation summary statement links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbConversationSummaryStatementLink 
	extends SingleRowModelAccess[ConversationSummaryStatementLink] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = ConversationSummaryStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationSummaryStatementLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted conversation summary statement link
	  * @return An access point to that conversation summary statement link
	  */
	def apply(id: Int) = DbSingleConversationSummaryStatementLink(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique conversation summary statement links.
	  * 
		@return An access point to the conversation summary statement link that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueConversationSummaryStatementLinkAccess(condition)
}

