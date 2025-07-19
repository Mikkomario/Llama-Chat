package vf.llama.database.access.single.conversation.session.summary.link.statement

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.SessionSummaryStatementLink

/**
  * An access point to individual session summary statement links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleSessionSummaryStatementLink(id: Int) 
	extends UniqueSessionSummaryStatementLinkAccess with SingleIntIdModelAccess[SessionSummaryStatementLink]

