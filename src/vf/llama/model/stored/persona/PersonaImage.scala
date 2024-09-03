package vf.llama.model.stored.persona

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.persona.image.DbSinglePersonaImage
import vf.llama.model.factory.persona.PersonaImageFactoryWrapper
import vf.llama.model.partial.persona.PersonaImageData

object PersonaImage extends StoredFromModelFactory[PersonaImageData, PersonaImage]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaImageData
	
	override protected def complete(model: AnyModel, data: PersonaImageData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona image that has already been stored in the database
  * @param id id of this persona image in the database
  * @param data Wrapped persona image data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaImage(id: Int, data: PersonaImageData) 
	extends StoredModelConvertible[PersonaImageData] with FromIdFactory[Int, PersonaImage] 
		with PersonaImageFactoryWrapper[PersonaImageData, PersonaImage]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona image in the database
	  */
	def access = DbSinglePersonaImage(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaImageData) = copy(data = data)
}

