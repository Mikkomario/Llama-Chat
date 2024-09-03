package vf.llama.database.access.single.conversation

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationDbFactory
import vf.llama.model.stored.conversation.Conversation

object UniqueConversationAccess extends ViewFactory[UniqueConversationAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueConversationAccess = 
		_UniqueConversationAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueConversationAccess(override val accessCondition: Option[Condition]) 
		extends UniqueConversationAccess
}

/**
  * A common trait for access points that return individual and distinct conversations.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueConversationAccess 
	extends UniqueConversationAccessLike[Conversation, UniqueConversationAccess] 
		with SingleChronoRowModelAccess[Conversation, UniqueConversationAccess] 
		with NullDeprecatableView[UniqueConversationAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueConversationAccess = UniqueConversationAccess(condition)
}

