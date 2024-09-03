package vf.llama.database.access.many.conversation

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.conversation.Conversation

/**
  * The root access point when targeting multiple conversations at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbConversations 
	extends ManyConversationsAccess with NonDeprecatedView[Conversation] 
		with ViewManyByIntIds[ManyConversationsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) conversations
	  */
	def includingHistory = DbConversationsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbConversationsIncludingHistory extends ManyConversationsAccess with UnconditionalView
}

