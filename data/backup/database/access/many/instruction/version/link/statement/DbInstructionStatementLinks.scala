package vf.llama.database.access.many.instruction.version.link.statement

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple instruction statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbInstructionStatementLinks 
	extends ManyInstructionStatementLinksAccess with UnconditionalView 
		with ViewManyByIntIds[ManyInstructionStatementLinksAccess]

