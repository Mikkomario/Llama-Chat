package vf.llama.model.stored.persona

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.persona.image.set.DbSinglePersonaImageSet
import vf.llama.model.factory.persona.PersonaImageSetFactoryWrapper
import vf.llama.model.partial.persona.PersonaImageSetData

object PersonaImageSet extends StoredFromModelFactory[PersonaImageSetData, PersonaImageSet]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaImageSetData
	
	override protected def complete(model: AnyModel, data: PersonaImageSetData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona image set that has already been stored in the database
  * @param id id of this persona image set in the database
  * @param data Wrapped persona image set data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaImageSet(id: Int, data: PersonaImageSetData) 
	extends StoredModelConvertible[PersonaImageSetData] with FromIdFactory[Int, PersonaImageSet] 
		with PersonaImageSetFactoryWrapper[PersonaImageSetData, PersonaImageSet]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona image set in the database
	  */
	def access = DbSinglePersonaImageSet(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaImageSetData) = copy(data = data)
}

