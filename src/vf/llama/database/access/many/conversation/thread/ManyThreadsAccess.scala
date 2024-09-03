package vf.llama.database.access.many.conversation.thread

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ThreadDbFactory
import vf.llama.model.stored.conversation.Thread

object ManyThreadsAccess extends ViewFactory[ManyThreadsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyThreadsAccess = _ManyThreadsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyThreadsAccess(override val accessCondition: Option[Condition]) 
		extends ManyThreadsAccess
}

/**
  * A common trait for access points which target multiple threads at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyThreadsAccess 
	extends ManyThreadsAccessLike[Thread, ManyThreadsAccess] with ManyRowModelAccess[Thread] 
		with ChronoRowFactoryView[Thread, ManyThreadsAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = ThreadDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyThreadsAccess = ManyThreadsAccess(condition)
}

