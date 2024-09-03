package vf.llama.database.access.many.persona.image.set

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.persona.PersonaLinkedImageSet

/**
  * The root access point when targeting multiple persona linked image sets at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaLinkedImageSets 
	extends ManyPersonaLinkedImageSetsAccess with NonDeprecatedView[PersonaLinkedImageSet] 
		with ViewManyByIntIds[ManyPersonaLinkedImageSetsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) persona linked image sets
	  */
	def includingHistory = DbPersonaLinkedImageSetsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbPersonaLinkedImageSetsIncludingHistory 
		extends ManyPersonaLinkedImageSetsAccess with UnconditionalView
}

