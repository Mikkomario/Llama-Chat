package vf.llama.database.access.many.conversation.session.summary.link.statement

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple session summary statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbSessionSummaryStatementLinks 
	extends ManySessionSummaryStatementLinksAccess with UnconditionalView 
		with ViewManyByIntIds[ManySessionSummaryStatementLinksAccess]

