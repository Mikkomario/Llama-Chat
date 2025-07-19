package vf.llama.database.access.single.conversation.thread

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.Thread

/**
  * An access point to individual threads, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleThread(id: Int) extends UniqueThreadAccess with SingleIntIdModelAccess[Thread]

