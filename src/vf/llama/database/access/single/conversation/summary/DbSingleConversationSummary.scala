package vf.llama.database.access.single.conversation.summary

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.ConversationSummary

/**
  * An access point to individual conversation summaries, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleConversationSummary(id: Int) 
	extends UniqueConversationSummaryAccess with SingleIntIdModelAccess[ConversationSummary]

