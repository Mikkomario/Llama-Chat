package vf.llama.database.access.single.instruction.link.persona

import utopia.vault.database.Connection
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.access.single.instruction.link.UniqueInstructionTargetingLinkAccessLike
import vf.llama.database.factory.instruction.PersonaInstructionLinkDbFactory
import vf.llama.database.storable.instruction.PersonaInstructionLinkDbModel
import vf.llama.model.stored.instruction.PersonaInstructionLink

object UniquePersonaInstructionLinkAccess extends ViewFactory[UniquePersonaInstructionLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaInstructionLinkAccess = 
		_UniquePersonaInstructionLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaInstructionLinkAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaInstructionLinkAccess
}

/**
  * A common trait for access points that return individual and distinct persona instruction links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaInstructionLinkAccess 
	extends UniqueInstructionTargetingLinkAccessLike[PersonaInstructionLink, UniquePersonaInstructionLinkAccess] 
		with NullDeprecatableView[UniquePersonaInstructionLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the persona to which the linked instruction applies. 
	  * None if no persona instruction link (or value) was found.
	  */
	def personaId(implicit connection: Connection) = targetId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaInstructionLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = PersonaInstructionLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaInstructionLinkAccess = 
		UniquePersonaInstructionLinkAccess(condition)
}

