package vf.llama.database.access.single.persona.image.animation.emotion

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.persona.AnimationEmotionAssignment

/**
  * An access point to individual animation emotion assignments, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleAnimationEmotionAssignment(id: Int) 
	extends UniqueAnimationEmotionAssignmentAccess with SingleIntIdModelAccess[AnimationEmotionAssignment]

