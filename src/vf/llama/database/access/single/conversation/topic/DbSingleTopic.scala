package vf.llama.database.access.single.conversation.topic

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.Topic

/**
  * An access point to individual topics, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleTopic(id: Int) extends UniqueTopicAccess with SingleIntIdModelAccess[Topic]

