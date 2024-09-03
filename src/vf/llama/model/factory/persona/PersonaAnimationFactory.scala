package vf.llama.model.factory.persona

import scala.concurrent.duration.FiniteDuration

import java.nio.file.Path

/**
  * Common trait for persona animation-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaAnimationFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param defaultFrameDuration New default frame duration to assign
	  * @return Copy of this item with the specified default frame duration
	  */
	def withDefaultFrameDuration(defaultFrameDuration: FiniteDuration): A
	
	/**
	  * @param relativeSubDirectory New relative sub directory to assign
	  * @return Copy of this item with the specified relative sub directory
	  */
	def withRelativeSubDirectory(relativeSubDirectory: Path): A
	
	/**
	  * @param setId New set id to assign
	  * @return Copy of this item with the specified set id
	  */
	def withSetId(setId: Int): A
}

