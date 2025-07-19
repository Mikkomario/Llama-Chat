package vf.llama.database.access.many.conversation.memory.link.statement

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple memory statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMemoryStatementLinks 
	extends ManyMemoryStatementLinksAccess with UnconditionalView 
		with ViewManyByIntIds[ManyMemoryStatementLinksAccess]

