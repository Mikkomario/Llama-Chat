package vf.llama.database.access.many.conversation

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationDbFactory
import vf.llama.model.stored.conversation.Conversation

object ManyConversationsAccess extends ViewFactory[ManyConversationsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override
		 def apply(condition: Condition): ManyConversationsAccess = _ManyConversationsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyConversationsAccess(override val accessCondition: Option[Condition]) 
		extends ManyConversationsAccess
}

/**
  * A common trait for access points which target multiple conversations at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyConversationsAccess 
	extends ManyConversationsAccessLike[Conversation, ManyConversationsAccess] 
		with ManyRowModelAccess[Conversation] with ChronoRowFactoryView[Conversation, ManyConversationsAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyConversationsAccess = ManyConversationsAccess(condition)
}

