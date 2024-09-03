package vf.llama.database.access.many.instruction

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple instructions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbInstructions 
	extends ManyInstructionsAccess with UnconditionalView with ViewManyByIntIds[ManyInstructionsAccess]

