package vf.llama.model.partial.instruction

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.time.Now
import vf.llama.model.factory.instruction.PersonaInstructionLinkFactory

import java.time.Instant

object PersonaInstructionLinkData extends FromModelFactoryWithSchema[PersonaInstructionLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("personaId", IntType, Vector("persona_id", "targetId", 
			"target_id")), PropertyDeclaration("instructionId", IntType, Single("instruction_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("removedAfter", InstantType, Single("removed_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaInstructionLinkData(valid("personaId").getInt, valid("instructionId").getInt, 
			valid("created").getInstant, valid("removedAfter").instant)
}

/**
  * Links an instruction to a persona to which it applies
  * @param personaId Id of the persona to which the linked instruction applies
  * @param instructionId Id of the instruction applied to the linked entity
  * @param created Time when this instruction targeting link was added to the database
  * @param removedAfter Time after which this link was removed
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaInstructionLinkData(personaId: Int, instructionId: Int, created: Instant = Now, 
	removedAfter: Option[Instant] = None) 
	extends PersonaInstructionLinkFactory[PersonaInstructionLinkData] with InstructionTargetingLinkData 
		with InstructionTargetingLinkDataLike[PersonaInstructionLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def targetId = personaId
	
	override def copyInstructionTargetingLink(targetId: Int, instructionId: Int, created: Instant, 
		removedAfter: Option[Instant]) = 
		copy(personaId = targetId, instructionId = instructionId, created = created, 
			removedAfter = removedAfter)
	
	override def withPersonaId(personaId: Int) = copy(personaId = personaId)
}

