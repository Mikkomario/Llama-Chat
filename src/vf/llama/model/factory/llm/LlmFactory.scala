package vf.llama.model.factory.llm

import java.time.Instant

/**
  * Common trait for llm-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait LlmFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param alias New alias to assign
	  * @return Copy of this item with the specified alias
	  */
	def withAlias(alias: String): A
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param name New name to assign
	  * @return Copy of this item with the specified name
	  */
	def withName(name: String): A
	
	/**
	  * @param removedAfter New removed after to assign
	  * @return Copy of this item with the specified removed after
	  */
	def withRemovedAfter(removedAfter: Instant): A
}

