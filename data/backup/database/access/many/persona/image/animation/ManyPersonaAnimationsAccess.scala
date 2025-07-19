package vf.llama.database.access.many.persona.image.animation

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaAnimationDbFactory
import vf.llama.model.stored.persona.PersonaAnimation

object ManyPersonaAnimationsAccess extends ViewFactory[ManyPersonaAnimationsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaAnimationsAccess = 
		_ManyPersonaAnimationsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaAnimationsAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaAnimationsAccess
}

/**
  * A common trait for access points which target multiple persona animations at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaAnimationsAccess 
	extends ManyPersonaAnimationsAccessLike[PersonaAnimation, ManyPersonaAnimationsAccess] 
		with ManyRowModelAccess[PersonaAnimation]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaAnimationDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): ManyPersonaAnimationsAccess = ManyPersonaAnimationsAccess(condition)
}

