package vf.llama.model.factory.conversation

import java.time.Instant

/**
  * Common trait for conversation-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ConversationFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param closedAfter New closed after to assign
	  * @return Copy of this item with the specified closed after
	  */
	def withClosedAfter(closedAfter: Instant): A
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param threadId New thread id to assign
	  * @return Copy of this item with the specified thread id
	  */
	def withThreadId(threadId: Int): A
}

