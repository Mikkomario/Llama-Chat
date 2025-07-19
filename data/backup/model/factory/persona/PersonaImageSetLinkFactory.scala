package vf.llama.model.factory.persona

import java.time.Instant

/**
  * Common trait for persona image set link-related factories which allow construction 
	with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaImageSetLinkFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param imageSetId New image set id to assign
	  * @return Copy of this item with the specified image set id
	  */
	def withImageSetId(imageSetId: Int): A
	
	/**
	  * @param personaId New persona id to assign
	  * @return Copy of this item with the specified persona id
	  */
	def withPersonaId(personaId: Int): A
	
	/**
	  * @param removedAfter New removed after to assign
	  * @return Copy of this item with the specified removed after
	  */
	def withRemovedAfter(removedAfter: Instant): A
}

