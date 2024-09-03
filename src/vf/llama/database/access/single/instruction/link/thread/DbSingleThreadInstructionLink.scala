package vf.llama.database.access.single.instruction.link.thread

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.instruction.ThreadInstructionLink

/**
  * An access point to individual thread instruction links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleThreadInstructionLink(id: Int) 
	extends UniqueThreadInstructionLinkAccess with SingleIntIdModelAccess[ThreadInstructionLink]

