package vf.llama.database.access.single.conversation.message

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.conversation.StatementLinkedMessage

/**
  * An access point to individual statement linked messages, based on their message id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleStatementLinkedMessage(id: Int) 
	extends UniqueStatementLinkedMessageAccess with SingleIntIdModelAccess[StatementLinkedMessage]

