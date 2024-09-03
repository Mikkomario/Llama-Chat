package vf.llama.model.combined.persona

import vf.llama.model.stored.persona.{PersonaAnimation, PersonaImage}

object PersonaAnimationWithFrames
{
	// OTHER	--------------------
	
	/**
	  * @param animation animation to wrap
	  * @param frame frame to attach to this animation
	  * @return Combination of the specified animation and frame
	  */
	def apply(animation: PersonaAnimation, frame: Seq[PersonaImage]): PersonaAnimationWithFrames = 
		_PersonaAnimationWithFrames(animation, frame)
	
	
	// NESTED	--------------------
	
	/**
	  * @param animation animation to wrap
	  * @param frame frame to attach to this animation
	  */
	private case class _PersonaAnimationWithFrames(animation: PersonaAnimation, frame: Seq[PersonaImage]) 
		extends PersonaAnimationWithFrames
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: PersonaAnimation) = copy(animation = factory)
	}
}

/**
  * Represents an animation with the images included
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaAnimationWithFrames extends CombinedPersonaAnimation[PersonaAnimationWithFrames]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped persona animation
	  */
	def animation: PersonaAnimation
	
	/**
	  * Frame that are attached to this animation
	  */
	def frame: Seq[PersonaImage]
	
	
	// IMPLEMENTED	--------------------
	
	override def personaAnimation = animation
}

