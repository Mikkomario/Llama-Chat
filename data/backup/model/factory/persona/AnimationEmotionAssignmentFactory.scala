package vf.llama.model.factory.persona

import java.time.Instant

/**
  * Common trait for animation emotion assignment-related factories which allow construction 
  * with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait AnimationEmotionAssignmentFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param animationId New animation id to assign
	  * @return Copy of this item with the specified animation id
	  */
	def withAnimationId(animationId: Int): A
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param deprecatedAfter New deprecated after to assign
	  * @return Copy of this item with the specified deprecated after
	  */
	def withDeprecatedAfter(deprecatedAfter: Instant): A
	
	/**
	  * @param emotion New emotion to assign
	  * @return Copy of this item with the specified emotion
	  */
	def withEmotion(emotion: String): A
	
	/**
	  * @param originLlmId New origin llm id to assign
	  * @return Copy of this item with the specified origin llm id
	  */
	def withOriginLlmId(originLlmId: Int): A
}

