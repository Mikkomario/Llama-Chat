package vf.llama.database.access.single.llm.variant

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.LlmSizeVariantDbFactory
import vf.llama.model.stored.llm.LlmSizeVariant

object UniqueLlmSizeVariantAccess extends ViewFactory[UniqueLlmSizeVariantAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueLlmSizeVariantAccess = 
		_UniqueLlmSizeVariantAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueLlmSizeVariantAccess(override val accessCondition: Option[Condition]) 
		extends UniqueLlmSizeVariantAccess
}

/**
  * A common trait for access points that return individual and distinct llm size variants.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueLlmSizeVariantAccess 
	extends UniqueLlmSizeVariantAccessLike[LlmSizeVariant, UniqueLlmSizeVariantAccess] 
		with SingleRowModelAccess[LlmSizeVariant] with NullDeprecatableView[UniqueLlmSizeVariantAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = LlmSizeVariantDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): UniqueLlmSizeVariantAccess = UniqueLlmSizeVariantAccess(condition)
}

