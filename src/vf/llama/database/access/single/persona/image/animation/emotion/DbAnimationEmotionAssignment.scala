package vf.llama.database.access.single.persona.image.animation.emotion

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.AnimationEmotionAssignmentDbFactory
import vf.llama.database.storable.persona.AnimationEmotionAssignmentDbModel
import vf.llama.model.stored.persona.AnimationEmotionAssignment

/**
  * Used for accessing individual animation emotion assignments
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbAnimationEmotionAssignment 
	extends SingleRowModelAccess[AnimationEmotionAssignment] 
		with NonDeprecatedView[AnimationEmotionAssignment] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = AnimationEmotionAssignmentDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = AnimationEmotionAssignmentDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted animation emotion assignment
	  * @return An access point to that animation emotion assignment
	  */
	def apply(id: Int) = DbSingleAnimationEmotionAssignment(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique animation emotion assignments.
	  * @return An access point to the animation emotion assignment that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueAnimationEmotionAssignmentAccess(mergeCondition(additionalCondition))
}

