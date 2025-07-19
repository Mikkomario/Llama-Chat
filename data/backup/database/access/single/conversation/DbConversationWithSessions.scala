package vf.llama.database.access.single.conversation

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationWithSessionsDbFactory
import vf.llama.database.storable.conversation.{ConversationDbModel, SessionDbModel}
import vf.llama.model.combined.conversation.ConversationWithSessions

/**
  * Used for accessing individual conversations with sessions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbConversationWithSessions 
	extends SingleModelAccess[ConversationWithSessions] with NonDeprecatedView[ConversationWithSessions] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked sessions
	  */
	protected def sessionModel = SessionDbModel
	
	/**
	  * A database model (factory) used for interacting with linked conversations
	  */
	private def model = ConversationDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationWithSessionsDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted conversation with sessions
	  * @return An access point to that conversation with sessions
	  */
	def apply(id: Int) = DbSingleConversationWithSessions(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield unique conversations 
	  * with sessions.
	  * @return An access point to the conversation with sessions that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueConversationWithSessionsAccess(mergeCondition(additionalCondition))
}

