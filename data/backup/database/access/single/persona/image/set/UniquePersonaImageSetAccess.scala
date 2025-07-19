package vf.llama.database.access.single.persona.image.set

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaImageSetDbFactory
import vf.llama.model.stored.persona.PersonaImageSet

object UniquePersonaImageSetAccess extends ViewFactory[UniquePersonaImageSetAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaImageSetAccess = 
		_UniquePersonaImageSetAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaImageSetAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaImageSetAccess
}

/**
  * A common trait for access points that return individual and distinct persona image sets.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaImageSetAccess 
	extends UniquePersonaImageSetAccessLike[PersonaImageSet, UniquePersonaImageSetAccess] 
		with SingleChronoRowModelAccess[PersonaImageSet, UniquePersonaImageSetAccess] 
		with NullDeprecatableView[UniquePersonaImageSetAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaImageSetDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): UniquePersonaImageSetAccess = UniquePersonaImageSetAccess(condition)
}

