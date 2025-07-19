package vf.llama.model.factory.conversation

import java.time.Instant

/**
  * Common trait for memory-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MemoryFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param archivedAfter New archived after to assign
	  * @return Copy of this item with the specified archived after
	  */
	def withArchivedAfter(archivedAfter: Instant): A
	
	/**
	  * @param messageId New message id to assign
	  * @return Copy of this item with the specified message id
	  */
	def withMessageId(messageId: Int): A
}

