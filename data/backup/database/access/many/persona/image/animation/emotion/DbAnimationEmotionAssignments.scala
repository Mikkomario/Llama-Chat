package vf.llama.database.access.many.persona.image.animation.emotion

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.persona.AnimationEmotionAssignment

/**
  * The root access point when targeting multiple animation emotion assignments at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbAnimationEmotionAssignments 
	extends ManyAnimationEmotionAssignmentsAccess with NonDeprecatedView[AnimationEmotionAssignment] 
		with ViewManyByIntIds[ManyAnimationEmotionAssignmentsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) animation emotion assignments
	  */
	def includingHistory = DbAnimationEmotionAssignmentsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbAnimationEmotionAssignmentsIncludingHistory 
		extends ManyAnimationEmotionAssignmentsAccess with UnconditionalView
}

