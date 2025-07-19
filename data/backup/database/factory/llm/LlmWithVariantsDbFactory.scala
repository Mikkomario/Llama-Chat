package vf.llama.database.factory.llm

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.llm.LlmWithVariants
import vf.llama.model.stored.llm.{Llm, LlmSizeVariant}

/**
  * Used for reading llm with variantses from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object LlmWithVariantsDbFactory 
	extends MultiCombiningFactory[LlmWithVariants, Llm, LlmSizeVariant] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = LlmSizeVariantDbFactory
	
	override def isAlwaysLinked = false
	
	override def nonDeprecatedCondition = 
		parentFactory.nonDeprecatedCondition && childFactory.nonDeprecatedCondition
	
	override def parentFactory = LlmDbFactory
	
	/**
	  * @param llm llm to wrap
	  * @param variant variant to attach to this llm
	  */
	override def apply(llm: Llm, variant: Seq[LlmSizeVariant]) = LlmWithVariants(llm, variant)
}

