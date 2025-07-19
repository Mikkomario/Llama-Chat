package vf.llama.database.access.many.conversation

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.conversation.ConversationWithSessions

/**
  * The root access point when targeting multiple conversations with sessions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbConversationsWithSessions 
	extends ManyConversationsWithSessionsAccess with NonDeprecatedView[ConversationWithSessions] 
		with ViewManyByIntIds[ManyConversationsWithSessionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) conversations with sessions
	  */
	def includingHistory = DbConversationsWithSessionsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbConversationsWithSessionsIncludingHistory 
		extends ManyConversationsWithSessionsAccess with UnconditionalView
}

