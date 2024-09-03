package vf.llama.model.factory.persona

import scala.concurrent.duration.FiniteDuration

/**
  * Common trait for persona image-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaImageFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param animationId New animation id to assign
	  * @return Copy of this item with the specified animation id
	  */
	def withAnimationId(animationId: Int): A
	
	/**
	  * @param customDuration New custom duration to assign
	  * @return Copy of this item with the specified custom duration
	  */
	def withCustomDuration(customDuration: FiniteDuration): A
	
	/**
	  * @param fileName New file name to assign
	  * @return Copy of this item with the specified file name
	  */
	def withFileName(fileName: String): A
	
	/**
	  * @param orderIndex New order index to assign
	  * @return Copy of this item with the specified order index
	  */
	def withOrderIndex(orderIndex: Int): A
}

