package vf.llama.database.access.single.instruction.version.link.statement

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.instruction.InstructionStatementLink

/**
  * An access point to individual instruction statement links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleInstructionStatementLink(id: Int) 
	extends UniqueInstructionStatementLinkAccess with SingleIntIdModelAccess[InstructionStatementLink]

