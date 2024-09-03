package vf.llama.database.access.single.llm.variant

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.ContextualLlmSizeVariantDbFactory
import vf.llama.database.storable.llm.{LlmDbModel, LlmSizeVariantDbModel}
import vf.llama.model.combined.llm.ContextualLlmSizeVariant

/**
  * Used for accessing individual contextual llm size variants
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbContextualLlmSizeVariant 
	extends SingleRowModelAccess[ContextualLlmSizeVariant] with NonDeprecatedView[ContextualLlmSizeVariant] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked llm
	  */
	protected def llmModel = LlmDbModel
	
	/**
	  * A database model (factory) used for interacting with linked variants
	  */
	private def model = LlmSizeVariantDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ContextualLlmSizeVariantDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted contextual llm size variant
	  * @return An access point to that contextual llm size variant
	  */
	def apply(id: Int) = DbSingleContextualLlmSizeVariant(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique contextual llm size variants.
	  * @return An access point to the contextual llm size variant that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueContextualLlmSizeVariantAccess(mergeCondition(additionalCondition))
}

