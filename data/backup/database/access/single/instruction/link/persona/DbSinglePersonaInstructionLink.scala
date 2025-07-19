package vf.llama.database.access.single.instruction.link.persona

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.instruction.PersonaInstructionLink

/**
  * An access point to individual persona instruction links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaInstructionLink(id: Int) 
	extends UniquePersonaInstructionLinkAccess with SingleIntIdModelAccess[PersonaInstructionLink]

