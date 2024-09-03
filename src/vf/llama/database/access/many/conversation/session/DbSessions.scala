package vf.llama.database.access.many.conversation.session

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.conversation.Session

/**
  * The root access point when targeting multiple sessions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbSessions 
	extends ManySessionsAccess with NonDeprecatedView[Session] with ViewManyByIntIds[ManySessionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) sessions
	  */
	def includingHistory = DbSessionsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbSessionsIncludingHistory extends ManySessionsAccess with UnconditionalView
}

