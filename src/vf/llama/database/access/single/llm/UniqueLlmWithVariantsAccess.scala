package vf.llama.database.access.single.llm

import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.LlmWithVariantsDbFactory
import vf.llama.database.storable.llm.LlmSizeVariantDbModel
import vf.llama.model.combined.llm.LlmWithVariants

object UniqueLlmWithVariantsAccess extends ViewFactory[UniqueLlmWithVariantsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueLlmWithVariantsAccess = 
		_UniqueLlmWithVariantsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueLlmWithVariantsAccess(override val accessCondition: Option[Condition]) 
		extends UniqueLlmWithVariantsAccess
}

/**
  * A common trait for access points that return distinct llm with variantses
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueLlmWithVariantsAccess 
	extends UniqueLlmAccessLike[LlmWithVariants, UniqueLlmWithVariantsAccess] 
		with NullDeprecatableView[UniqueLlmWithVariantsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked variant
	  */
	protected def variantModel = LlmSizeVariantDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = LlmWithVariantsDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): UniqueLlmWithVariantsAccess = UniqueLlmWithVariantsAccess(condition)
}

