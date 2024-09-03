package vf.llama.database.access.single.conversation

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.Conversation

/**
  * An access point to individual conversations, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleConversation(id: Int) 
	extends UniqueConversationAccess with SingleIntIdModelAccess[Conversation]

