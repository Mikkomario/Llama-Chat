package vf.llama.database.factory.llm

import utopia.vault.nosql.factory.row.linked.CombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.llm.ContextualLlmSizeVariant
import vf.llama.model.stored.llm.{Llm, LlmSizeVariant}

/**
  * Used for reading contextual llm size variants from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ContextualLlmSizeVariantDbFactory 
	extends CombiningFactory[ContextualLlmSizeVariant, LlmSizeVariant, Llm] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = LlmDbFactory
	
	override def nonDeprecatedCondition = 
		parentFactory.nonDeprecatedCondition && childFactory.nonDeprecatedCondition
	
	override def parentFactory = LlmSizeVariantDbFactory
	
	/**
	  * @param variant variant to wrap
	  * @param llm llm to attach to this variant
	  */
	override def apply(variant: LlmSizeVariant, llm: Llm) = ContextualLlmSizeVariant(variant, llm)
}

