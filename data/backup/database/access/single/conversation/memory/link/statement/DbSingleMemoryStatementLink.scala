package vf.llama.database.access.single.conversation.memory.link.statement

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.MemoryStatementLink

/**
  * An access point to individual memory statement links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleMemoryStatementLink(id: Int) 
	extends UniqueMemoryStatementLinkAccess with SingleIntIdModelAccess[MemoryStatementLink]

