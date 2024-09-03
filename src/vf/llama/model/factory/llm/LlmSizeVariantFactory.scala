package vf.llama.model.factory.llm

import java.time.Instant

/**
  * Common trait for llm size variant-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait LlmSizeVariantFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param llmId New llm id to assign
	  * @return Copy of this item with the specified llm id
	  */
	def withLlmId(llmId: Int): A
	
	/**
	  * @param removedAfter New removed after to assign
	  * @return Copy of this item with the specified removed after
	  */
	def withRemovedAfter(removedAfter: Instant): A
	
	/**
	  * @param size New size to assign
	  * @return Copy of this item with the specified size
	  */
	def withSize(size: Int): A
	
	/**
	  * @param suffix New suffix to assign
	  * @return Copy of this item with the specified suffix
	  */
	def withSuffix(suffix: String): A
}

