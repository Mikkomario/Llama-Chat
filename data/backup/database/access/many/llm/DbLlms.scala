package vf.llama.database.access.many.llm

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.llm.Llm

/**
  * The root access point when targeting multiple llms at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbLlms extends ManyLlmsAccess with NonDeprecatedView[Llm] with ViewManyByIntIds[ManyLlmsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) llms
	  */
	def includingHistory = DbLlmsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbLlmsIncludingHistory extends ManyLlmsAccess with UnconditionalView
}

