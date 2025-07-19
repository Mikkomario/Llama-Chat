package vf.llama.database.access.single.llm.variant

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.LlmSizeVariantDbFactory
import vf.llama.database.storable.llm.LlmSizeVariantDbModel
import vf.llama.model.stored.llm.LlmSizeVariant

/**
  * Used for accessing individual llm size variants
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbLlmSizeVariant 
	extends SingleRowModelAccess[LlmSizeVariant] with NonDeprecatedView[LlmSizeVariant] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = LlmSizeVariantDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = LlmSizeVariantDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted llm size variant
	  * @return An access point to that llm size variant
	  */
	def apply(id: Int) = DbSingleLlmSizeVariant(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique llm size variants.
	  * @return An access point to the llm size variant that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueLlmSizeVariantAccess(mergeCondition(additionalCondition))
}

