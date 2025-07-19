package vf.llama.model.factory.instruction

/**
  * Common trait for classes that implement TopicInstructionLinkFactory by wrapping
  *  a TopicInstructionLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait TopicInstructionLinkFactoryWrapper[A <: TopicInstructionLinkFactory[A], +Repr] 
	extends TopicInstructionLinkFactory[Repr] with InstructionTargetingLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withTopicId(topicId: Int) = withTargetId(topicId)
}

