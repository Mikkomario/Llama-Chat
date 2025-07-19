package vf.llama.database.access.single.conversation.topic.settings

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.TopicSettings

/**
  * An access point to individual topic settings, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleTopicSettings(id: Int) 
	extends UniqueTopicSettingsAccess with SingleIntIdModelAccess[TopicSettings]

