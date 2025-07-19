package vf.llama.database.access.single.conversation

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.conversation.ConversationWithSessions

/**
  * An access point to individual conversations with sessions, based on their conversation id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleConversationWithSessions(id: Int) 
	extends UniqueConversationWithSessionsAccess with SingleIntIdModelAccess[ConversationWithSessions]

