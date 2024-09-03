package vf.llama.database.access.single.instruction.link.topic

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.instruction.TopicInstructionLink

/**
  * An access point to individual topic instruction links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleTopicInstructionLink(id: Int) 
	extends UniqueTopicInstructionLinkAccess with SingleIntIdModelAccess[TopicInstructionLink]

