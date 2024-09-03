package vf.llama.model.factory.persona

import java.nio.file.Path
import java.time.Instant

/**
  * Common trait for persona image set-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaImageSetFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param relativeDirectory New relative directory to assign
	  * @return Copy of this item with the specified relative directory
	  */
	def withRelativeDirectory(relativeDirectory: Path): A
	
	/**
	  * @param removedAfter New removed after to assign
	  * @return Copy of this item with the specified removed after
	  */
	def withRemovedAfter(removedAfter: Instant): A
}

