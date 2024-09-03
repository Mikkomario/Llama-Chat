package vf.llama.database.access.single.llm

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.LlmWithVariantsDbFactory
import vf.llama.database.storable.llm.{LlmDbModel, LlmSizeVariantDbModel}
import vf.llama.model.combined.llm.LlmWithVariants

/**
  * Used for accessing individual llm with variantses
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbLlmWithVariants 
	extends SingleModelAccess[LlmWithVariants] with NonDeprecatedView[LlmWithVariants] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked variant
	  */
	protected def variantModel = LlmSizeVariantDbModel
	
	/**
	  * A database model (factory) used for interacting with linked llms
	  */
	private def model = LlmDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = LlmWithVariantsDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted llm with variants
	  * @return An access point to that llm with variants
	  */
	def apply(id: Int) = DbSingleLlmWithVariants(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield unique llm 
	  * with variantses.
	  * @return An access point to the llm with variants that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueLlmWithVariantsAccess(mergeCondition(additionalCondition))
}

