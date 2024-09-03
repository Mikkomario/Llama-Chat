package vf.llama.model.stored.persona

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.persona.DbSinglePersona
import vf.llama.model.factory.persona.PersonaFactoryWrapper
import vf.llama.model.partial.persona.PersonaData

object Persona extends StoredFromModelFactory[PersonaData, Persona]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaData
	
	override protected def complete(model: AnyModel, data: PersonaData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona that has already been stored in the database
  * @param id id of this persona in the database
  * @param data Wrapped persona data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Persona(id: Int, data: PersonaData) 
	extends StoredModelConvertible[PersonaData] with FromIdFactory[Int, Persona] 
		with PersonaFactoryWrapper[PersonaData, Persona]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona in the database
	  */
	def access = DbSinglePersona(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaData) = copy(data = data)
}

