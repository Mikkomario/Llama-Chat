package vf.llama.model.stored.meta

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.meta.availability.persona.DbSinglePersonaInfoAvailability
import vf.llama.model.factory.meta.PersonaInfoAvailabilityFactoryWrapper
import vf.llama.model.partial.meta.{MetaInfoAvailabilityData, PersonaInfoAvailabilityData}

object PersonaInfoAvailability 
	extends StoredFromModelFactory[PersonaInfoAvailabilityData, PersonaInfoAvailability]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaInfoAvailabilityData
	
	override protected def complete(model: AnyModel, data: PersonaInfoAvailabilityData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona info availability that has already been stored in the database
  * @param id id of this persona info availability in the database
  * @param data Wrapped persona info availability data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaInfoAvailability(id: Int, data: PersonaInfoAvailabilityData) 
	extends PersonaInfoAvailabilityFactoryWrapper[PersonaInfoAvailabilityData, PersonaInfoAvailability] 
		with MetaInfoAvailabilityData 
		with StoredMetaInfoAvailabilityLike[PersonaInfoAvailabilityData, PersonaInfoAvailability]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona info availability in the database
	  */
	def access = DbSinglePersonaInfoAvailability(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaInfoAvailabilityData) = copy(data = data)
}

