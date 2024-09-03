package vf.llama.database.access.single.llm.variant

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.llm.ContextualLlmSizeVariant

/**
  * An access point to individual contextual llm size variants, based on their variant id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleContextualLlmSizeVariant(id: Int) 
	extends UniqueContextualLlmSizeVariantAccess with SingleIntIdModelAccess[ContextualLlmSizeVariant]

