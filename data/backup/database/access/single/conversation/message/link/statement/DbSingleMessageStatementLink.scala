package vf.llama.database.access.single.conversation.message.link.statement

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.MessageStatementLink

/**
  * An access point to individual message statement links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleMessageStatementLink(id: Int) 
	extends UniqueMessageStatementLinkAccess with SingleIntIdModelAccess[MessageStatementLink]

