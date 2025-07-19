package vf.llama.model.combined.llm

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.llm.LlmSizeVariantFactoryWrapper
import vf.llama.model.partial.llm.LlmSizeVariantData
import vf.llama.model.stored.llm.{Llm, LlmSizeVariant}

object ContextualLlmSizeVariant
{
	// OTHER	--------------------
	
	/**
	  * @param variant variant to wrap
	  * @param llm llm to attach to this variant
	  * @return Combination of the specified variant and llm
	  */
	def apply(variant: LlmSizeVariant, llm: Llm): ContextualLlmSizeVariant = 
		_ContextualLlmSizeVariant(variant, llm)
	
	
	// NESTED	--------------------
	
	/**
	  * @param variant variant to wrap
	  * @param llm llm to attach to this variant
	  */
	private case class _ContextualLlmSizeVariant(variant: LlmSizeVariant, llm: Llm) 
		extends ContextualLlmSizeVariant
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: LlmSizeVariant) = copy(variant = factory)
	}
}

/**
  * Attaches LLM's information to a specific size variant
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ContextualLlmSizeVariant 
	extends Extender[LlmSizeVariantData] with HasId[Int] 
		with LlmSizeVariantFactoryWrapper[LlmSizeVariant, ContextualLlmSizeVariant]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped variant
	  */
	def variant: LlmSizeVariant
	
	/**
	  * The llm that is attached to this variant
	  */
	def llm: Llm
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this variant in the database
	  */
	override def id = variant.id
	
	override def wrapped = variant.data
	
	override protected def wrappedFactory = variant
}

