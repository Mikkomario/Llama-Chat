package vf.llama.model.stored.persona

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.persona.image.animation.DbSinglePersonaAnimation
import vf.llama.model.factory.persona.PersonaAnimationFactoryWrapper
import vf.llama.model.partial.persona.PersonaAnimationData

object PersonaAnimation extends StoredFromModelFactory[PersonaAnimationData, PersonaAnimation]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaAnimationData
	
	override protected def complete(model: AnyModel, data: PersonaAnimationData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona animation that has already been stored in the database
  * @param id id of this persona animation in the database
  * @param data Wrapped persona animation data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaAnimation(id: Int, data: PersonaAnimationData) 
	extends StoredModelConvertible[PersonaAnimationData] with FromIdFactory[Int, PersonaAnimation] 
		with PersonaAnimationFactoryWrapper[PersonaAnimationData, PersonaAnimation]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona animation in the database
	  */
	def access = DbSinglePersonaAnimation(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaAnimationData) = copy(data = data)
}

