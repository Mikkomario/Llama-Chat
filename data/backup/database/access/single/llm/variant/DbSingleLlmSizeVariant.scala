package vf.llama.database.access.single.llm.variant

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.llm.LlmSizeVariant

/**
  * An access point to individual llm size variants, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleLlmSizeVariant(id: Int) 
	extends UniqueLlmSizeVariantAccess with SingleIntIdModelAccess[LlmSizeVariant]

