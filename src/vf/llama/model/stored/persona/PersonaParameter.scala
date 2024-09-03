package vf.llama.model.stored.persona

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.persona.settings.parameter.DbSinglePersonaParameter
import vf.llama.model.factory.persona.PersonaParameterFactoryWrapper
import vf.llama.model.partial.persona.PersonaParameterData

object PersonaParameter extends StoredFromModelFactory[PersonaParameterData, PersonaParameter]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaParameterData
	
	override protected def complete(model: AnyModel, data: PersonaParameterData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona parameter that has already been stored in the database
  * @param id id of this persona parameter in the database
  * @param data Wrapped persona parameter data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaParameter(id: Int, data: PersonaParameterData) 
	extends StoredModelConvertible[PersonaParameterData] with FromIdFactory[Int, PersonaParameter] 
		with PersonaParameterFactoryWrapper[PersonaParameterData, PersonaParameter]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona parameter in the database
	  */
	def access = DbSinglePersonaParameter(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaParameterData) = copy(data = data)
}

