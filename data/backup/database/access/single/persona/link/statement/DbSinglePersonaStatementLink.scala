package vf.llama.database.access.single.persona.link.statement

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.persona.PersonaStatementLink

/**
  * An access point to individual persona statement links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaStatementLink(id: Int) 
	extends UniquePersonaStatementLinkAccess with SingleIntIdModelAccess[PersonaStatementLink]

