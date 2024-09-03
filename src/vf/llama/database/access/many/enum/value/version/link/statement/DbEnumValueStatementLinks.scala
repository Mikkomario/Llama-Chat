package vf.llama.database.access.many.`enum`.value.version.link.statement

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple enum value statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbEnumValueStatementLinks 
	extends ManyEnumValueStatementLinksAccess with UnconditionalView 
		with ViewManyByIntIds[ManyEnumValueStatementLinksAccess]

