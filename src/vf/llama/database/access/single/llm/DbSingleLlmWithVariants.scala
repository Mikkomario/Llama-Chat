package vf.llama.database.access.single.llm

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.llm.LlmWithVariants

/**
  * An access point to individual llm with variantses, based on their llm id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleLlmWithVariants(id: Int) 
	extends UniqueLlmWithVariantsAccess with SingleIntIdModelAccess[LlmWithVariants]

