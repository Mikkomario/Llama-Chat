package vf.llama.model.stored.persona

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.persona.image.animation.emotion.DbSingleAnimationEmotionAssignment
import vf.llama.model.factory.persona.AnimationEmotionAssignmentFactoryWrapper
import vf.llama.model.partial.persona.AnimationEmotionAssignmentData

object AnimationEmotionAssignment 
	extends StoredFromModelFactory[AnimationEmotionAssignmentData, AnimationEmotionAssignment]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = AnimationEmotionAssignmentData
	
	override protected def complete(model: AnyModel, data: AnimationEmotionAssignmentData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a animation emotion assignment that has already been stored in the database
  * @param id id of this animation emotion assignment in the database
  * @param data Wrapped animation emotion assignment data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class AnimationEmotionAssignment(id: Int, data: AnimationEmotionAssignmentData) 
	extends StoredModelConvertible[AnimationEmotionAssignmentData] 
		with FromIdFactory[Int, AnimationEmotionAssignment] 
		with AnimationEmotionAssignmentFactoryWrapper[AnimationEmotionAssignmentData, AnimationEmotionAssignment]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this animation emotion assignment in the database
	  */
	def access = DbSingleAnimationEmotionAssignment(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: AnimationEmotionAssignmentData) = copy(data = data)
}

