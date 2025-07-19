package vf.llama.database.access.single.conversation.session

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.conversation.Session

/**
  * An access point to individual sessions, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleSession(id: Int) extends UniqueSessionAccess with SingleIntIdModelAccess[Session]

