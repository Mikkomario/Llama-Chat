package vf.llama.model.factory.conversation

import java.time.Instant

/**
  * Common trait for thread-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ThreadFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param archivedAfter New archived after to assign
	  * @return Copy of this item with the specified archived after
	  */
	def withArchivedAfter(archivedAfter: Instant): A
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param name New name to assign
	  * @return Copy of this item with the specified name
	  */
	def withName(name: String): A
	
	/**
	  * @param topicId New topic id to assign
	  * @return Copy of this item with the specified topic id
	  */
	def withTopicId(topicId: Int): A
}

