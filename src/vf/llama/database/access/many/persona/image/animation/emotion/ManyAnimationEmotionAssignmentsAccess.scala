package vf.llama.database.access.many.persona.image.animation.emotion

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.AnimationEmotionAssignmentDbFactory
import vf.llama.database.storable.persona.AnimationEmotionAssignmentDbModel
import vf.llama.model.stored.persona.AnimationEmotionAssignment

import java.time.Instant

object ManyAnimationEmotionAssignmentsAccess extends ViewFactory[ManyAnimationEmotionAssignmentsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyAnimationEmotionAssignmentsAccess = 
		_ManyAnimationEmotionAssignmentsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyAnimationEmotionAssignmentsAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManyAnimationEmotionAssignmentsAccess
}

/**
  * A common trait for access points which target multiple animation emotion assignments at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyAnimationEmotionAssignmentsAccess 
	extends ManyRowModelAccess[AnimationEmotionAssignment] 
		with NullDeprecatableView[ManyAnimationEmotionAssignmentsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * animation ids of the accessible animation emotion assignments
	  */
	def animationIds(implicit connection: Connection) = pullColumn(model.animationId.column)
		.map { v => v.getInt }
	
	/**
	  * emotions of the accessible animation emotion assignments
	  */
	def emotions(implicit connection: Connection) = pullColumn(model.emotion.column).flatMap { _.string }
	
	/**
	  * origin llm ids of the accessible animation emotion assignments
	  */
	def originLlmIds(implicit connection: Connection) = 
		pullColumn(model.originLlmId.column).flatMap { v => v.int }
	
	/**
	  * creation times of the accessible animation emotion assignments
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible animation emotion assignments
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible animation emotion assignments
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = AnimationEmotionAssignmentDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = AnimationEmotionAssignmentDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyAnimationEmotionAssignmentsAccess = 
		ManyAnimationEmotionAssignmentsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param originLlmId origin llm id to target
	  * @return Copy of this access point that only includes animation emotion assignments 
	  * with the specified origin llm id
	  */
	def byLlmVariant(originLlmId: Int) = filter(model.originLlmId.column <=> originLlmId)
	
	/**
	  * @param originLlmIds Targeted origin llm ids
	  * 
		@return Copy of this access point that only includes animation emotion assignments where origin llm id is
	  *  within the specified value set
	  */
	def byLlmVariants(originLlmIds: IterableOnce[Int]) = 
		filter(model.originLlmId.column.in(IntSet.from(originLlmIds)))
	
	/**
	  * Updates the deprecation times of the targeted animation emotion assignments
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any animation emotion assignment was affected
	  */
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
	
	/**
	  * @param animationId animation id to target
	  * @return Copy of this access point that only includes animation emotion assignments 
	  * with the specified animation id
	  */
	def forAnimation(animationId: Int) = filter(model.animationId.column <=> animationId)
	
	/**
	  * @param animationIds Targeted animation ids
	  * @return Copy of this access point that only includes animation emotion assignments where animation id
	  *  is within the specified value set
	  */
	def forAnimations(animationIds: IterableOnce[Int]) = 
		filter(model.animationId.column.in(IntSet.from(animationIds)))
	
	/**
	  * Updates the origin llm ids of the targeted animation emotion assignments
	  * @param newOriginLlmId A new origin llm id to assign
	  * @return Whether any animation emotion assignment was affected
	  */
	def originLlmIds_=(newOriginLlmId: Int)(implicit connection: Connection) = 
		putColumn(model.originLlmId.column, newOriginLlmId)
}

