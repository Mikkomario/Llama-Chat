package vf.llama.database.access.single.conversation.thread

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ThreadDbFactory
import vf.llama.model.stored.conversation.Thread

object UniqueThreadAccess extends ViewFactory[UniqueThreadAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueThreadAccess = _UniqueThreadAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueThreadAccess(override val accessCondition: Option[Condition]) 
		extends UniqueThreadAccess
}

/**
  * A common trait for access points that return individual and distinct threads.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueThreadAccess 
	extends UniqueThreadAccessLike[Thread, UniqueThreadAccess] 
		with SingleChronoRowModelAccess[Thread, UniqueThreadAccess] 
		with NullDeprecatableView[UniqueThreadAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = ThreadDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueThreadAccess = UniqueThreadAccess(condition)
}

