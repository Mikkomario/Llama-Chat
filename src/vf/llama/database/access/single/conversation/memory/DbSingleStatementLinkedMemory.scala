package vf.llama.database.access.single.conversation.memory

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.conversation.StatementLinkedMemory

/**
  * An access point to individual statement linked memories, based on their memory id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleStatementLinkedMemory(id: Int) 
	extends UniqueStatementLinkedMemoryAccess with SingleIntIdModelAccess[StatementLinkedMemory]

