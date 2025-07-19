package vf.llama.database.access.single.instruction.link.thread

import utopia.vault.database.Connection
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.access.single.instruction.link.UniqueInstructionTargetingLinkAccessLike
import vf.llama.database.factory.instruction.ThreadInstructionLinkDbFactory
import vf.llama.database.storable.instruction.ThreadInstructionLinkDbModel
import vf.llama.model.stored.instruction.ThreadInstructionLink

object UniqueThreadInstructionLinkAccess extends ViewFactory[UniqueThreadInstructionLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueThreadInstructionLinkAccess = 
		_UniqueThreadInstructionLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueThreadInstructionLinkAccess(override val accessCondition: Option[Condition]) 
		extends UniqueThreadInstructionLinkAccess
}

/**
  * A common trait for access points that return individual and distinct thread instruction links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueThreadInstructionLinkAccess 
	extends UniqueInstructionTargetingLinkAccessLike[ThreadInstructionLink, UniqueThreadInstructionLinkAccess] 
		with NullDeprecatableView[UniqueThreadInstructionLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the thread where the linked instruction applies. 
	  * None if no thread instruction link (or value) was found.
	  */
	def threadId(implicit connection: Connection) = targetId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ThreadInstructionLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = ThreadInstructionLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueThreadInstructionLinkAccess = 
		UniqueThreadInstructionLinkAccess(condition)
}

