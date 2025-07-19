package vf.llama.database.access.single.conversation

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationDbFactory
import vf.llama.database.storable.conversation.ConversationDbModel
import vf.llama.model.stored.conversation.Conversation

/**
  * Used for accessing individual conversations
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbConversation 
	extends SingleRowModelAccess[Conversation] with NonDeprecatedView[Conversation] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = ConversationDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted conversation
	  * @return An access point to that conversation
	  */
	def apply(id: Int) = DbSingleConversation(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique conversations.
	  * @return An access point to the conversation that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueConversationAccess(mergeCondition(additionalCondition))
}

