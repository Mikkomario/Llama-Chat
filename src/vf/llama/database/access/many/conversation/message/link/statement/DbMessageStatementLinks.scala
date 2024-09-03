package vf.llama.database.access.many.conversation.message.link.statement

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple message statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMessageStatementLinks 
	extends ManyMessageStatementLinksAccess with UnconditionalView 
		with ViewManyByIntIds[ManyMessageStatementLinksAccess]

