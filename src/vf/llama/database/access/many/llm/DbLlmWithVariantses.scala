package vf.llama.database.access.many.llm

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.llm.LlmWithVariants

/**
  * The root access point when targeting multiple llm with variantses at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbLlmWithVariantses 
	extends ManyLlmWithVariantsesAccess with NonDeprecatedView[LlmWithVariants] 
		with ViewManyByIntIds[ManyLlmWithVariantsesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) llm with variantses
	  */
	def includingHistory = DbLlmWithVariantsesIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbLlmWithVariantsesIncludingHistory extends ManyLlmWithVariantsesAccess with UnconditionalView
}

