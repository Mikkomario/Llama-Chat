package vf.llama.database.access.many.conversation.topic

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.conversation.TopicWithSettings

/**
  * The root access point when targeting multiple topics with settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbTopicsWithSettings 
	extends ManyTopicsWithSettingsAccess with NonDeprecatedView[TopicWithSettings] 
		with ViewManyByIntIds[ManyTopicsWithSettingsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) topics with settings
	  */
	def includingHistory = DbTopicsWithSettingsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbTopicsWithSettingsIncludingHistory extends ManyTopicsWithSettingsAccess with UnconditionalView
}

