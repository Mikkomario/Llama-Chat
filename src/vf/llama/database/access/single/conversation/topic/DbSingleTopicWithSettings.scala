package vf.llama.database.access.single.conversation.topic

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.conversation.TopicWithSettings

/**
  * An access point to individual topics with settings, based on their topic id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleTopicWithSettings(id: Int) 
	extends UniqueTopicWithSettingsAccess with SingleIntIdModelAccess[TopicWithSettings]

