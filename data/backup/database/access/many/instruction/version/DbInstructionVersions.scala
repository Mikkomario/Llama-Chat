package vf.llama.database.access.many.instruction.version

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.instruction.InstructionVersion

/**
  * The root access point when targeting multiple instruction versions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbInstructionVersions 
	extends ManyInstructionVersionsAccess with NonDeprecatedView[InstructionVersion] 
		with ViewManyByIntIds[ManyInstructionVersionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) instruction versions
	  */
	def includingHistory = DbInstructionVersionsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbInstructionVersionsIncludingHistory extends ManyInstructionVersionsAccess with UnconditionalView
}

