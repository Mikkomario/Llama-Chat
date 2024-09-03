package vf.llama.database.access.many.persona.image.set

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.persona.PersonaImageSet

/**
  * The root access point when targeting multiple persona image sets at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaImageSets 
	extends ManyPersonaImageSetsAccess with NonDeprecatedView[PersonaImageSet] 
		with ViewManyByIntIds[ManyPersonaImageSetsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) persona image sets
	  */
	def includingHistory = DbPersonaImageSetsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbPersonaImageSetsIncludingHistory extends ManyPersonaImageSetsAccess with UnconditionalView
}

