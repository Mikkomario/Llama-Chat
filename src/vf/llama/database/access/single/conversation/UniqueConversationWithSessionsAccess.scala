package vf.llama.database.access.single.conversation

import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationWithSessionsDbFactory
import vf.llama.database.storable.conversation.SessionDbModel
import vf.llama.model.combined.conversation.ConversationWithSessions

object UniqueConversationWithSessionsAccess extends ViewFactory[UniqueConversationWithSessionsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueConversationWithSessionsAccess = 
		_UniqueConversationWithSessionsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueConversationWithSessionsAccess(override val accessCondition: Option[Condition]) 
		extends UniqueConversationWithSessionsAccess
}

/**
  * A common trait for access points that return distinct conversations with sessions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueConversationWithSessionsAccess 
	extends UniqueConversationAccessLike[ConversationWithSessions, UniqueConversationWithSessionsAccess] 
		with NullDeprecatableView[UniqueConversationWithSessionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked sessions
	  */
	protected def sessionModel = SessionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationWithSessionsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueConversationWithSessionsAccess = 
		UniqueConversationWithSessionsAccess(condition)
}

