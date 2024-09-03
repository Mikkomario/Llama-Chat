package vf.llama.database.factory.conversation

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.conversation.ConversationWithSessions
import vf.llama.model.stored.conversation.{Conversation, Session}

/**
  * Used for reading conversations with sessions from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ConversationWithSessionsDbFactory 
	extends MultiCombiningFactory[ConversationWithSessions, Conversation, Session] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = SessionDbFactory
	
	override def isAlwaysLinked = true
	
	override def nonDeprecatedCondition = 
		parentFactory.nonDeprecatedCondition && childFactory.nonDeprecatedCondition
	
	override def parentFactory = ConversationDbFactory
	
	/**
	  * @param conversation conversation to wrap
	  * @param sessions sessions to attach to this conversation
	  */
	override def apply(conversation: Conversation, sessions: Seq[Session]) = 
		ConversationWithSessions(conversation, sessions)
}

