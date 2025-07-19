package vf.llama.database.access.single.conversation.summary.link.statement

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.ConversationSummaryStatementLink

/**
  * An access point to individual conversation summary statement links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleConversationSummaryStatementLink(id: Int) 
	extends UniqueConversationSummaryStatementLinkAccess 
		with SingleIntIdModelAccess[ConversationSummaryStatementLink]

