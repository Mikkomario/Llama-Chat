package vf.llama.database.access.single.persona

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaDbFactory
import vf.llama.model.stored.persona.Persona

object UniquePersonaAccess extends ViewFactory[UniquePersonaAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaAccess = _UniquePersonaAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaAccess
}

/**
  * A common trait for access points that return individual and distinct personas.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaAccess 
	extends UniquePersonaAccessLike[Persona, UniquePersonaAccess] with SingleRowModelAccess[Persona] 
		with NullDeprecatableView[UniquePersonaAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaAccess = UniquePersonaAccess(condition)
}

