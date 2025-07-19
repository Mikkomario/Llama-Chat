package vf.llama.database.access.many.persona

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaDbFactory
import vf.llama.model.stored.persona.Persona

object ManyPersonasAccess extends ViewFactory[ManyPersonasAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonasAccess = _ManyPersonasAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonasAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonasAccess
}

/**
  * A common trait for access points which target multiple personas at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonasAccess 
	extends ManyPersonasAccessLike[Persona, ManyPersonasAccess] with ManyRowModelAccess[Persona]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonasAccess = ManyPersonasAccess(condition)
}

