package vf.llama.database.access.many.persona.image.link

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.persona.PersonaImageSetLink

/**
  * The root access point when targeting multiple persona image set links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaImageSetLinks 
	extends ManyPersonaImageSetLinksAccess with NonDeprecatedView[PersonaImageSetLink] 
		with ViewManyByIntIds[ManyPersonaImageSetLinksAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) persona image set links
	  */
	def includingHistory = DbPersonaImageSetLinksIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbPersonaImageSetLinksIncludingHistory extends ManyPersonaImageSetLinksAccess 
		with UnconditionalView
}

