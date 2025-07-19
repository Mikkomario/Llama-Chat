package vf.llama.database.access.single.instruction

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.instruction.Instruction

/**
  * An access point to individual instructions, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleInstruction(id: Int) 
	extends UniqueInstructionAccess with SingleIntIdModelAccess[Instruction]

