package vf.llama.model.factory.meta

/**
  * Common trait for classes that implement TopicInfoAvailabilityFactory by wrapping
  *  a TopicInfoAvailabilityFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait TopicInfoAvailabilityFactoryWrapper[A <: TopicInfoAvailabilityFactory[A], +Repr] 
	extends TopicInfoAvailabilityFactory[Repr] with MetaInfoAvailabilityFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withTopicId(topicId: Int) = withContextId(topicId)
}

