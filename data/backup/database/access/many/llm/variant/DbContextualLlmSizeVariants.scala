package vf.llama.database.access.many.llm.variant

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.llm.ContextualLlmSizeVariant

/**
  * The root access point when targeting multiple contextual llm size variants at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbContextualLlmSizeVariants 
	extends ManyContextualLlmSizeVariantsAccess with NonDeprecatedView[ContextualLlmSizeVariant] 
		with ViewManyByIntIds[ManyContextualLlmSizeVariantsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) contextual llm size variants
	  */
	def includingHistory = DbContextualLlmSizeVariantsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbContextualLlmSizeVariantsIncludingHistory 
		extends ManyContextualLlmSizeVariantsAccess with UnconditionalView
}

