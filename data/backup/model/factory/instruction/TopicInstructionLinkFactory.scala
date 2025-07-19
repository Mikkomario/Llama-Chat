package vf.llama.model.factory.instruction

/**
  * Common trait for topic instruction link-related factories which allow construction 
	with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait TopicInstructionLinkFactory[+A] extends InstructionTargetingLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param topicId New topic id to assign
	  * @return Copy of this item with the specified topic id
	  */
	def withTopicId(topicId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withTargetId(targetId: Int) = withTopicId(targetId)
}

