package vf.llama.model.factory.meta

/**
  * Common trait for topic info availability-related factories which allow construction 
	with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait TopicInfoAvailabilityFactory[+A] extends MetaInfoAvailabilityFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param topicId New topic id to assign
	  * @return Copy of this item with the specified topic id
	  */
	def withTopicId(topicId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withContextId(contextId: Int) = withTopicId(contextId)
}

