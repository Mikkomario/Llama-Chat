package vf.llama.database.storable.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.persona.AnimationEmotionAssignmentFactory
import vf.llama.model.partial.persona.AnimationEmotionAssignmentData
import vf.llama.model.stored.persona.AnimationEmotionAssignment

import java.time.Instant

/**
  * Used for constructing AnimationEmotionAssignmentDbModel instances and for inserting animation
  *  emotion assignments to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object AnimationEmotionAssignmentDbModel 
	extends StorableFactory[AnimationEmotionAssignmentDbModel, AnimationEmotionAssignment, AnimationEmotionAssignmentData] 
		with FromIdFactory[Int, AnimationEmotionAssignmentDbModel] with HasIdProperty 
		with AnimationEmotionAssignmentFactory[AnimationEmotionAssignmentDbModel] 
		with DeprecatableAfter[AnimationEmotionAssignmentDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with animation ids
	  */
	lazy val animationId = property("animationId")
	
	/**
	  * Database property used for interacting with emotions
	  */
	lazy val emotion = property("emotion")
	
	/**
	  * Database property used for interacting with origin llm ids
	  */
	lazy val originLlmId = property("originLlmId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with deprecation times
	  */
	lazy val deprecatedAfter = property("deprecatedAfter")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.animationEmotionAssignment
	
	override def apply(data: AnimationEmotionAssignmentData) = 
		apply(None, Some(data.animationId), data.emotion, data.originLlmId, Some(data.created), 
			data.deprecatedAfter)
	
	/**
	  * @param animationId Id of the animation this assignment describes
	  * @return A model containing only the specified animation id
	  */
	override def withAnimationId(animationId: Int) = apply(animationId = Some(animationId))
	
	/**
	  * @param created Time when this animation emotion assignment was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this assignment was replaced or retracted
	  * @return A model containing only the specified deprecated after
	  */
	override
		 def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param emotion Name of the emotion assigned to this animation
	  * @return A model containing only the specified emotion
	  */
	override def withEmotion(emotion: String) = apply(emotion = emotion)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * 
		@param originLlmId Id of the LLM (variant) which assigned this emotion. None if not assigned by a (known) LLM.
	  * @return A model containing only the specified origin llm id
	  */
	override def withOriginLlmId(originLlmId: Int) = apply(originLlmId = Some(originLlmId))
	
	override protected def complete(id: Value, data: AnimationEmotionAssignmentData) = 
		AnimationEmotionAssignment(id.getInt, data)
}

/**
  * Used for interacting with AnimationEmotionAssignments in the database
  * @param id animation emotion assignment database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class AnimationEmotionAssignmentDbModel(id: Option[Int] = None, animationId: Option[Int] = None, 
	emotion: String = "", originLlmId: Option[Int] = None, created: Option[Instant] = None, 
	deprecatedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, AnimationEmotionAssignmentDbModel] 
		with AnimationEmotionAssignmentFactory[AnimationEmotionAssignmentDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = AnimationEmotionAssignmentDbModel.table
	
	override def valueProperties = 
		Vector(AnimationEmotionAssignmentDbModel.id.name -> id, 
			AnimationEmotionAssignmentDbModel.animationId.name -> animationId, 
			AnimationEmotionAssignmentDbModel.emotion.name -> emotion, 
			AnimationEmotionAssignmentDbModel.originLlmId.name -> originLlmId, 
			AnimationEmotionAssignmentDbModel.created.name -> created, 
			AnimationEmotionAssignmentDbModel.deprecatedAfter.name -> deprecatedAfter)
	
	/**
	  * @param animationId Id of the animation this assignment describes
	  * @return A new copy of this model with the specified animation id
	  */
	override def withAnimationId(animationId: Int) = copy(animationId = Some(animationId))
	
	/**
	  * @param created Time when this animation emotion assignment was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this assignment was replaced or retracted
	  * @return A new copy of this model with the specified deprecated after
	  */
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param emotion Name of the emotion assigned to this animation
	  * @return A new copy of this model with the specified emotion
	  */
	override def withEmotion(emotion: String) = copy(emotion = emotion)
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * 
		@param originLlmId Id of the LLM (variant) which assigned this emotion. None if not assigned by a (known) LLM.
	  * @return A new copy of this model with the specified origin llm id
	  */
	override def withOriginLlmId(originLlmId: Int) = copy(originLlmId = Some(originLlmId))
}

