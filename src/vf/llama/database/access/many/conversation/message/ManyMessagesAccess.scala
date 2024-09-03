package vf.llama.database.access.many.conversation.message

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.MessageDbFactory
import vf.llama.model.stored.conversation.Message

object ManyMessagesAccess extends ViewFactory[ManyMessagesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyMessagesAccess = _ManyMessagesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyMessagesAccess(override val accessCondition: Option[Condition]) 
		extends ManyMessagesAccess
}

/**
  * A common trait for access points which target multiple messages at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyMessagesAccess 
	extends ManyMessagesAccessLike[Message, ManyMessagesAccess] with ManyRowModelAccess[Message] 
		with ChronoRowFactoryView[Message, ManyMessagesAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MessageDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyMessagesAccess = ManyMessagesAccess(condition)
}

