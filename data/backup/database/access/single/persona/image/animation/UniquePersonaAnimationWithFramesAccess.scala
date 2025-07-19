package vf.llama.database.access.single.persona.image.animation

import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaAnimationWithFramesDbFactory
import vf.llama.database.storable.persona.PersonaImageDbModel
import vf.llama.model.combined.persona.PersonaAnimationWithFrames

object UniquePersonaAnimationWithFramesAccess extends ViewFactory[UniquePersonaAnimationWithFramesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaAnimationWithFramesAccess = 
		_UniquePersonaAnimationWithFramesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaAnimationWithFramesAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniquePersonaAnimationWithFramesAccess
}

/**
  * A common trait for access points that return distinct persona animations with image frames
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaAnimationWithFramesAccess 
	extends UniquePersonaAnimationAccessLike[PersonaAnimationWithFrames, 
		UniquePersonaAnimationWithFramesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked frame
	  */
	protected def frameModel = PersonaImageDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaAnimationWithFramesDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaAnimationWithFramesAccess = 
		UniquePersonaAnimationWithFramesAccess(condition)
}

