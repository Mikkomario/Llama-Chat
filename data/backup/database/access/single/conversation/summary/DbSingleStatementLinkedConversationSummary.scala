package vf.llama.database.access.single.conversation.summary

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.conversation.StatementLinkedConversationSummary

/**
  * An access point to individual statement linked conversation summaries, based on their summary id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleStatementLinkedConversationSummary(id: Int) 
	extends UniqueStatementLinkedConversationSummaryAccess 
		with SingleIntIdModelAccess[StatementLinkedConversationSummary]

