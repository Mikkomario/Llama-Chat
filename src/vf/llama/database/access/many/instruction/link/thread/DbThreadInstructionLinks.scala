package vf.llama.database.access.many.instruction.link.thread

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.instruction.ThreadInstructionLink

/**
  * The root access point when targeting multiple thread instruction links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbThreadInstructionLinks 
	extends ManyThreadInstructionLinksAccess with NonDeprecatedView[ThreadInstructionLink] 
		with ViewManyByIntIds[ManyThreadInstructionLinksAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) thread instruction links
	  */
	def includingHistory = DbThreadInstructionLinksIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbThreadInstructionLinksIncludingHistory 
		extends ManyThreadInstructionLinksAccess with UnconditionalView
}

