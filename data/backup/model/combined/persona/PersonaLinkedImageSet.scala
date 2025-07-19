package vf.llama.model.combined.persona

import vf.llama.model.stored.persona.{PersonaImageSet, PersonaImageSetLink}

object PersonaLinkedImageSet
{
	// OTHER	--------------------
	
	/**
	  * @param imageSet image set to wrap
	  * @param link link to attach to this image set
	  * @return Combination of the specified image set and link
	  */
	def apply(imageSet: PersonaImageSet, link: PersonaImageSetLink): PersonaLinkedImageSet = 
		_PersonaLinkedImageSet(imageSet, link)
	
	
	// NESTED	--------------------
	
	/**
	  * @param imageSet image set to wrap
	  * @param link link to attach to this image set
	  */
	private case class _PersonaLinkedImageSet(imageSet: PersonaImageSet, link: PersonaImageSetLink) 
		extends PersonaLinkedImageSet
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: PersonaImageSet) = copy(imageSet = factory)
	}
}

/**
  * Represents an image set used for a specific persona
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaLinkedImageSet extends CombinedPersonaImageSet[PersonaLinkedImageSet]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped persona image set
	  */
	def imageSet: PersonaImageSet
	
	/**
	  * The link that is attached to this image set
	  */
	def link: PersonaImageSetLink
	
	
	// IMPLEMENTED	--------------------
	
	override def personaImageSet = imageSet
}

