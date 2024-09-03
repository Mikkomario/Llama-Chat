package vf.llama.model.stored.persona

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.persona.image.link.DbSinglePersonaImageSetLink
import vf.llama.model.factory.persona.PersonaImageSetLinkFactoryWrapper
import vf.llama.model.partial.persona.PersonaImageSetLinkData

object PersonaImageSetLink extends StoredFromModelFactory[PersonaImageSetLinkData, PersonaImageSetLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaImageSetLinkData
	
	override protected def complete(model: AnyModel, data: PersonaImageSetLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona image set link that has already been stored in the database
  * @param id id of this persona image set link in the database
  * @param data Wrapped persona image set link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaImageSetLink(id: Int, data: PersonaImageSetLinkData) 
	extends StoredModelConvertible[PersonaImageSetLinkData] with FromIdFactory[Int, PersonaImageSetLink] 
		with PersonaImageSetLinkFactoryWrapper[PersonaImageSetLinkData, PersonaImageSetLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona image set link in the database
	  */
	def access = DbSinglePersonaImageSetLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaImageSetLinkData) = copy(data = data)
}

