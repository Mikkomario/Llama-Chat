package vf.llama.database.factory.persona

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import vf.llama.model.combined.persona.PersonaAnimationWithFrames
import vf.llama.model.stored.persona.{PersonaAnimation, PersonaImage}

/**
  * Used for reading persona animations with image frames from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaAnimationWithFramesDbFactory 
	extends MultiCombiningFactory[PersonaAnimationWithFrames, PersonaAnimation, PersonaImage]
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = PersonaImageDbFactory
	
	override def isAlwaysLinked = true
	
	override def parentFactory = PersonaAnimationDbFactory
	
	/**
	  * @param animation animation to wrap
	  * @param frame frame to attach to this animation
	  */
	override def apply(animation: PersonaAnimation, frame: Seq[PersonaImage]) = 
		PersonaAnimationWithFrames(animation, frame)
}

