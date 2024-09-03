package vf.llama.database.access.single.persona.image.animation

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaAnimationDbFactory
import vf.llama.model.stored.persona.PersonaAnimation

object UniquePersonaAnimationAccess extends ViewFactory[UniquePersonaAnimationAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaAnimationAccess = 
		_UniquePersonaAnimationAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaAnimationAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaAnimationAccess
}

/**
  * A common trait for access points that return individual and distinct persona animations.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaAnimationAccess 
	extends UniquePersonaAnimationAccessLike[PersonaAnimation, UniquePersonaAnimationAccess] 
		with SingleRowModelAccess[PersonaAnimation]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaAnimationDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaAnimationAccess = 
		UniquePersonaAnimationAccess(condition)
}

