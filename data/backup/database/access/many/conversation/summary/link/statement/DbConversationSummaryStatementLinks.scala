package vf.llama.database.access.many.conversation.summary.link.statement

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple conversation summary statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbConversationSummaryStatementLinks 
	extends ManyConversationSummaryStatementLinksAccess with UnconditionalView 
		with ViewManyByIntIds[ManyConversationSummaryStatementLinksAccess]

