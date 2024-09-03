package vf.llama.model.factory.conversation

import utopia.echo.model.enumeration.ChatRole

import java.time.Instant

/**
  * Common trait for message-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MessageFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param sender New sender to assign
	  * @return Copy of this item with the specified sender
	  */
	def withSender(sender: ChatRole): A
	
	/**
	  * @param sessionId New session id to assign
	  * @return Copy of this item with the specified session id
	  */
	def withSessionId(sessionId: Int): A
}

