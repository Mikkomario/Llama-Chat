package vf.llama.database.access.many.conversation.thread

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.conversation.Thread

/**
  * The root access point when targeting multiple threads at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbThreads 
	extends ManyThreadsAccess with NonDeprecatedView[Thread] with ViewManyByIntIds[ManyThreadsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) threads
	  */
	def includingHistory = DbThreadsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbThreadsIncludingHistory extends ManyThreadsAccess with UnconditionalView
}

