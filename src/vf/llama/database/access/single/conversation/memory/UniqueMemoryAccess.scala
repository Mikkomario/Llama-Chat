package vf.llama.database.access.single.conversation.memory

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.MemoryDbFactory
import vf.llama.model.stored.conversation.Memory

object UniqueMemoryAccess extends ViewFactory[UniqueMemoryAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueMemoryAccess = _UniqueMemoryAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueMemoryAccess(override val accessCondition: Option[Condition]) 
		extends UniqueMemoryAccess
}

/**
  * A common trait for access points that return individual and distinct memories.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueMemoryAccess 
	extends UniqueMemoryAccessLike[Memory, UniqueMemoryAccess] with SingleRowModelAccess[Memory] 
		with NullDeprecatableView[UniqueMemoryAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MemoryDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueMemoryAccess = UniqueMemoryAccess(condition)
}

