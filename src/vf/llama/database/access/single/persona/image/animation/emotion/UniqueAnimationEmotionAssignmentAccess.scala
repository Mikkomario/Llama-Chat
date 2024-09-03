package vf.llama.database.access.single.persona.image.animation.emotion

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.AnimationEmotionAssignmentDbFactory
import vf.llama.database.storable.persona.AnimationEmotionAssignmentDbModel
import vf.llama.model.stored.persona.AnimationEmotionAssignment

import java.time.Instant

object UniqueAnimationEmotionAssignmentAccess extends ViewFactory[UniqueAnimationEmotionAssignmentAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueAnimationEmotionAssignmentAccess = 
		_UniqueAnimationEmotionAssignmentAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueAnimationEmotionAssignmentAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniqueAnimationEmotionAssignmentAccess
}

/**
  * A common trait for access points that return individual and distinct animation emotion assignments.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueAnimationEmotionAssignmentAccess 
	extends SingleRowModelAccess[AnimationEmotionAssignment] 
		with DistinctModelAccess[AnimationEmotionAssignment, Option[AnimationEmotionAssignment], Value] 
		with NullDeprecatableView[UniqueAnimationEmotionAssignmentAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the animation this assignment describes. 
	  * None if no animation emotion assignment (or value) was found.
	  */
	def animationId(implicit connection: Connection) = pullColumn(model.animationId.column).int
	
	/**
	  * Name of the emotion assigned to this animation. 
	  * None if no animation emotion assignment (or value) was found.
	  */
	def emotion(implicit connection: Connection) = pullColumn(model.emotion.column).getString
	
	/**
	  * Id of the LLM (variant) which assigned this emotion. None if not assigned by a (known) LLM. 
	  * None if no animation emotion assignment (or value) was found.
	  */
	def originLlmId(implicit connection: Connection) = pullColumn(model.originLlmId.column).int
	
	/**
	  * Time when this animation emotion assignment was added to the database. 
	  * None if no animation emotion assignment (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this assignment was replaced or retracted. 
	  * None if no animation emotion assignment (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfter.column).instant
	
	/**
	  * Unique id of the accessible animation emotion assignment. None if no animation emotion assignment
	  *  was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = AnimationEmotionAssignmentDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = AnimationEmotionAssignmentDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueAnimationEmotionAssignmentAccess = 
		UniqueAnimationEmotionAssignmentAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted animation emotion assignments
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any animation emotion assignment was affected
	  */
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
	
	/**
	  * Updates the origin llm ids of the targeted animation emotion assignments
	  * @param newOriginLlmId A new origin llm id to assign
	  * @return Whether any animation emotion assignment was affected
	  */
	def originLlmId_=(newOriginLlmId: Int)(implicit connection: Connection) = 
		putColumn(model.originLlmId.column, newOriginLlmId)
}

