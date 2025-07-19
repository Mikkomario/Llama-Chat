package vf.llama.database.access.many.conversation.topic

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.TopicDbFactory
import vf.llama.model.stored.conversation.Topic

object ManyTopicsAccess extends ViewFactory[ManyTopicsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyTopicsAccess = _ManyTopicsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyTopicsAccess(override val accessCondition: Option[Condition])
		 extends ManyTopicsAccess
}

/**
  * A common trait for access points which target multiple topics at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyTopicsAccess 
	extends ManyTopicsAccessLike[Topic, ManyTopicsAccess] with ManyRowModelAccess[Topic] 
		with ChronoRowFactoryView[Topic, ManyTopicsAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = TopicDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyTopicsAccess = ManyTopicsAccess(condition)
}

