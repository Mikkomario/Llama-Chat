package vf.llama.database.access.many.conversation.topic.settings

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.conversation.TopicSettings

/**
  * The root access point when targeting multiple topic settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbManyTopicSettings 
	extends ManyTopicSettingsAccess with NonDeprecatedView[TopicSettings] 
		with ViewManyByIntIds[ManyTopicSettingsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) topic settings
	  */
	def includingHistory = DbTopicSettingsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbTopicSettingsIncludingHistory extends ManyTopicSettingsAccess with UnconditionalView
}

