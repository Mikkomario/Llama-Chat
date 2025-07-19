package vf.llama.database.access.many.llm.variant

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.LlmSizeVariantDbFactory
import vf.llama.model.stored.llm.LlmSizeVariant

object ManyLlmSizeVariantsAccess extends ViewFactory[ManyLlmSizeVariantsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyLlmSizeVariantsAccess = 
		_ManyLlmSizeVariantsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyLlmSizeVariantsAccess(override val accessCondition: Option[Condition]) 
		extends ManyLlmSizeVariantsAccess
}

/**
  * A common trait for access points which target multiple llm size variants at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyLlmSizeVariantsAccess 
	extends ManyLlmSizeVariantsAccessLike[LlmSizeVariant, ManyLlmSizeVariantsAccess] 
		with ManyRowModelAccess[LlmSizeVariant]
{
	// IMPLEMENTED	--------------------
	
	override def factory = LlmSizeVariantDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyLlmSizeVariantsAccess = ManyLlmSizeVariantsAccess(condition)
}

