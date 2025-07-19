package vf.llama.model.factory.conversation

import java.time.Instant

/**
  * Common trait for session-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait SessionFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param conversationId New conversation id to assign
	  * @return Copy of this item with the specified conversation id
	  */
	def withConversationId(conversationId: Int): A
	
	/**
	  * @param ended New ended to assign
	  * @return Copy of this item with the specified ended
	  */
	def withEnded(ended: Instant): A
	
	/**
	  * @param started New started to assign
	  * @return Copy of this item with the specified started
	  */
	def withStarted(started: Instant): A
}

