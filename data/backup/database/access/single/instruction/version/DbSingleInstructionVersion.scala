package vf.llama.database.access.single.instruction.version

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.instruction.InstructionVersion

/**
  * An access point to individual instruction versions, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleInstructionVersion(id: Int) 
	extends UniqueInstructionVersionAccess with SingleIntIdModelAccess[InstructionVersion]

