package vf.llama.model.combined.llm

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.llm.LlmFactoryWrapper
import vf.llama.model.partial.llm.LlmData
import vf.llama.model.stored.llm.{Llm, LlmSizeVariant}

object LlmWithVariants
{
	// OTHER	--------------------
	
	/**
	  * @param llm llm to wrap
	  * @param variant variant to attach to this llm
	  * @return Combination of the specified llm and variant
	  */
	def apply(llm: Llm, variant: Seq[LlmSizeVariant]): LlmWithVariants = _LlmWithVariants(llm, variant)
	
	
	// NESTED	--------------------
	
	/**
	  * @param llm llm to wrap
	  * @param variant variant to attach to this llm
	  */
	private case class _LlmWithVariants(llm: Llm, variant: Seq[LlmSizeVariant]) extends LlmWithVariants
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: Llm) = copy(llm = factory)
	}
}

/**
  * Includes LLM's size variants with the base LLM model
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait LlmWithVariants extends Extender[LlmData] with HasId[Int] with LlmFactoryWrapper[Llm, LlmWithVariants]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped llm
	  */
	def llm: Llm
	
	/**
	  * Variant that are attached to this llm
	  */
	def variant: Seq[LlmSizeVariant]
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this llm in the database
	  */
	override def id = llm.id
	
	override def wrapped = llm.data
	
	override protected def wrappedFactory = llm
}

