package vf.llama.database.access.many.persona.link.statement

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple persona statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaStatementLinks 
	extends ManyPersonaStatementLinksAccess with UnconditionalView 
		with ViewManyByIntIds[ManyPersonaStatementLinksAccess]

