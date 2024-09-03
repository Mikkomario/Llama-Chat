package vf.llama.database.access.many.instruction.link.persona

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.instruction.PersonaInstructionLink

/**
  * The root access point when targeting multiple persona instruction links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaInstructionLinks 
	extends ManyPersonaInstructionLinksAccess with NonDeprecatedView[PersonaInstructionLink] 
		with ViewManyByIntIds[ManyPersonaInstructionLinksAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) persona instruction links
	  */
	def includingHistory = DbPersonaInstructionLinksIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbPersonaInstructionLinksIncludingHistory 
		extends ManyPersonaInstructionLinksAccess with UnconditionalView
}

