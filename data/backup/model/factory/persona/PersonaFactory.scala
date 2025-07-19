package vf.llama.model.factory.persona

import java.time.Instant

/**
  * Common trait for persona-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param deletedAfter New deleted after to assign
	  * @return Copy of this item with the specified deleted after
	  */
	def withDeletedAfter(deletedAfter: Instant): A
}

