package vf.llama.database.access.single.conversation.memory

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.Memory

/**
  * An access point to individual memories, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleMemory(id: Int) extends UniqueMemoryAccess with SingleIntIdModelAccess[Memory]

