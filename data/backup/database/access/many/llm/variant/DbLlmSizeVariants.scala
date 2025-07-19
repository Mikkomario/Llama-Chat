package vf.llama.database.access.many.llm.variant

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.llm.LlmSizeVariant

/**
  * The root access point when targeting multiple llm size variants at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbLlmSizeVariants 
	extends ManyLlmSizeVariantsAccess with NonDeprecatedView[LlmSizeVariant] 
		with ViewManyByIntIds[ManyLlmSizeVariantsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) llm size variants
	  */
	def includingHistory = DbLlmSizeVariantsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbLlmSizeVariantsIncludingHistory extends ManyLlmSizeVariantsAccess with UnconditionalView
}

