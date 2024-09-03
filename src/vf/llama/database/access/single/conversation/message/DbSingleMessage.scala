package vf.llama.database.access.single.conversation.message

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.Message

/**
  * An access point to individual messages, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleMessage(id: Int) extends UniqueMessageAccess with SingleIntIdModelAccess[Message]

