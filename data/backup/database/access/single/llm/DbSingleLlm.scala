package vf.llama.database.access.single.llm

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.llm.Llm

/**
  * An access point to individual llms, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleLlm(id: Int) extends UniqueLlmAccess with SingleIntIdModelAccess[Llm]

