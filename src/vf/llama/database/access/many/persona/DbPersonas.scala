package vf.llama.database.access.many.persona

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.persona.Persona

/**
  * The root access point when targeting multiple personas at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonas 
	extends ManyPersonasAccess with NonDeprecatedView[Persona] with ViewManyByIntIds[ManyPersonasAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) personas
	  */
	def includingHistory = DbPersonasIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbPersonasIncludingHistory extends ManyPersonasAccess with UnconditionalView
}

