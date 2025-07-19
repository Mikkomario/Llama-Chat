package vf.llama.database.access.single.conversation.session

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.conversation.SummaryStatementLinkedSession

/**
  * An access point to individual summary statement linked sessions, based on their session id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleSummaryStatementLinkedSession(id: Int) 
	extends UniqueSummaryStatementLinkedSessionAccess 
		with SingleIntIdModelAccess[SummaryStatementLinkedSession]

