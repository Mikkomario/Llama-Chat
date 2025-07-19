package vf.llama.model.stored.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.instruction.link.persona.DbSinglePersonaInstructionLink
import vf.llama.model.factory.instruction.PersonaInstructionLinkFactoryWrapper
import vf.llama.model.partial.instruction.{InstructionTargetingLinkData, PersonaInstructionLinkData}

object PersonaInstructionLink 
	extends StoredFromModelFactory[PersonaInstructionLinkData, PersonaInstructionLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaInstructionLinkData
	
	override protected def complete(model: AnyModel, data: PersonaInstructionLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona instruction link that has already been stored in the database
  * @param id id of this persona instruction link in the database
  * @param data Wrapped persona instruction link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaInstructionLink(id: Int, data: PersonaInstructionLinkData) 
	extends PersonaInstructionLinkFactoryWrapper[PersonaInstructionLinkData, PersonaInstructionLink] 
		with InstructionTargetingLinkData 
		with StoredInstructionTargetingLinkLike[PersonaInstructionLinkData, PersonaInstructionLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona instruction link in the database
	  */
	def access = DbSinglePersonaInstructionLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaInstructionLinkData) = copy(data = data)
}

