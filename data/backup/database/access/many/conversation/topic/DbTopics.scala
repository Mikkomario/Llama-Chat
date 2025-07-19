package vf.llama.database.access.many.conversation.topic

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.conversation.Topic

/**
  * The root access point when targeting multiple topics at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbTopics extends ManyTopicsAccess with NonDeprecatedView[Topic] with ViewManyByIntIds[ManyTopicsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) topics
	  */
	def includingHistory = DbTopicsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbTopicsIncludingHistory extends ManyTopicsAccess with UnconditionalView
}

