package vf.llama.database.access.many.conversation.memory

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.MemoryDbFactory
import vf.llama.model.stored.conversation.Memory

object ManyMemoriesAccess extends ViewFactory[ManyMemoriesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyMemoriesAccess = _ManyMemoriesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyMemoriesAccess(override val accessCondition: Option[Condition]) 
		extends ManyMemoriesAccess
}

/**
  * A common trait for access points which target multiple memories at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyMemoriesAccess 
	extends ManyMemoriesAccessLike[Memory, ManyMemoriesAccess] with ManyRowModelAccess[Memory]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MemoryDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyMemoriesAccess = ManyMemoriesAccess(condition)
}

