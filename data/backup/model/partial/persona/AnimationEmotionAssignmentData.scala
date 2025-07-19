package vf.llama.model.partial.persona

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.persona.AnimationEmotionAssignmentFactory

import java.time.Instant

object AnimationEmotionAssignmentData extends FromModelFactoryWithSchema[AnimationEmotionAssignmentData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("animationId", IntType, Single("animation_id")), 
			PropertyDeclaration("emotion", StringType), PropertyDeclaration("originLlmId", IntType, 
			Single("origin_llm_id"), isOptional = true), PropertyDeclaration("created", InstantType, 
			isOptional = true), PropertyDeclaration("deprecatedAfter", InstantType, 
			Single("deprecated_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		AnimationEmotionAssignmentData(valid("animationId").getInt, valid("emotion").getString, 
			valid("originLlmId").int, valid("created").getInstant, valid("deprecatedAfter").instant)
}

/**
  * Contains an assessment of an animation's emotional expression
  * @param animationId Id of the animation this assignment describes
  * @param emotion Name of the emotion assigned to this animation
  * 
	@param originLlmId Id of the LLM (variant) which assigned this emotion. None if not assigned by a (known) LLM.
  * @param created Time when this animation emotion assignment was added to the database
  * @param deprecatedAfter Time when this assignment was replaced or retracted
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class AnimationEmotionAssignmentData(animationId: Int, emotion: String, originLlmId: Option[Int] = None, 
	created: Instant = Now, deprecatedAfter: Option[Instant] = None) 
	extends AnimationEmotionAssignmentFactory[AnimationEmotionAssignmentData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this animation emotion assignment has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this animation emotion assignment is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("animationId" -> animationId, "emotion" -> emotion, "originLlmId" -> originLlmId, 
			"created" -> created, "deprecatedAfter" -> deprecatedAfter))
	
	override def withAnimationId(animationId: Int) = copy(animationId = animationId)
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withEmotion(emotion: String) = copy(emotion = emotion)
	
	override def withOriginLlmId(originLlmId: Int) = copy(originLlmId = Some(originLlmId))
}

