package vf.llama.model.combined.conversation

import vf.llama.model.stored.conversation.{Conversation, Session}

object ConversationWithSessions
{
	// OTHER	--------------------
	
	/**
	  * @param conversation conversation to wrap
	  * @param sessions sessions to attach to this conversation
	  * @return Combination of the specified conversation and session
	  */
	def apply(conversation: Conversation, sessions: Seq[Session]): ConversationWithSessions = 
		_ConversationWithSessions(conversation, sessions)
	
	
	// NESTED	--------------------
	
	/**
	  * @param conversation conversation to wrap
	  * @param sessions sessions to attach to this conversation
	  */
	private case class _ConversationWithSessions(conversation: Conversation, sessions: Seq[Session]) 
		extends ConversationWithSessions
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: Conversation) = copy(conversation = factory)
	}
}

/**
  * Links individual sessions to a conversation
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ConversationWithSessions extends CombinedConversation[ConversationWithSessions]
{
	// ABSTRACT	--------------------
	
	/**
	  * Sessions that are attached to this conversation
	  */
	def sessions: Seq[Session]
}

