package vf.llama.database.access.many.conversation.memory

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.conversation.Memory

/**
  * The root access point when targeting multiple memories at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMemories 
	extends ManyMemoriesAccess with NonDeprecatedView[Memory] with ViewManyByIntIds[ManyMemoriesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) memories
	  */
	def includingHistory = DbMemoriesIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbMemoriesIncludingHistory extends ManyMemoriesAccess with UnconditionalView
}

