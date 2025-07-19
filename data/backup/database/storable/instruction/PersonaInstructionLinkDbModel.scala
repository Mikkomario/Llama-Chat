package vf.llama.database.storable.instruction

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.instruction.InstructionTargetingLinkDbProps
import vf.llama.model.factory.instruction.PersonaInstructionLinkFactory
import vf.llama.model.partial.instruction.PersonaInstructionLinkData
import vf.llama.model.stored.instruction.PersonaInstructionLink

import java.time.Instant

/**
  * Used for constructing PersonaInstructionLinkDbModel instances and for inserting persona instruction links
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaInstructionLinkDbModel 
	extends InstructionTargetingLinkDbModelFactoryLike[PersonaInstructionLinkDbModel, PersonaInstructionLink, PersonaInstructionLinkData] 
		with PersonaInstructionLinkFactory[PersonaInstructionLinkDbModel] 
		with InstructionTargetingLinkDbProps with NullDeprecatable[PersonaInstructionLinkDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with persona ids
	  */
	lazy val personaId = property("personaId")
	
	/**
	  * Database property used for interacting with instruction ids
	  */
	override lazy val instructionId = property("instructionId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	override lazy val created = property("created")
	
	/**
	  * Database property used for interacting with remove times
	  */
	override lazy val removedAfter = property("removedAfter")
	
	override val deprecationAttName = "removedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.personaInstructionLink
	
	override def targetId = personaId
	
	override def apply(data: PersonaInstructionLinkData) = 
		apply(None, Some(data.personaId), Some(data.instructionId), Some(data.created), data.removedAfter)
	
	/**
	  * @param created Time when this instruction targeting link was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withRemovedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param instructionId Id of the instruction applied to the linked entity
	  * @return A model containing only the specified instruction id
	  */
	override def withInstructionId(instructionId: Int) = apply(instructionId = Some(instructionId))
	
	/**
	  * @param personaId Id of the persona to which the linked instruction applies
	  * @return A model containing only the specified persona id
	  */
	override def withPersonaId(personaId: Int) = apply(personaId = Some(personaId))
	
	/**
	  * @param removedAfter Time after which this link was removed
	  * @return A model containing only the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = apply(removedAfter = Some(removedAfter))
	
	override protected def complete(id: Value, data: PersonaInstructionLinkData) = 
		PersonaInstructionLink(id.getInt, data)
}

/**
  * Used for interacting with PersonaInstructionLinks in the database
  * @param id persona instruction link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaInstructionLinkDbModel(id: Option[Int] = None, personaId: Option[Int] = None, 
	instructionId: Option[Int] = None, created: Option[Instant] = None, removedAfter: Option[Instant] = None) 
	extends InstructionTargetingLinkDbModel 
		with InstructionTargetingLinkDbModelLike[PersonaInstructionLinkDbModel] 
		with PersonaInstructionLinkFactory[PersonaInstructionLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = PersonaInstructionLinkDbModel
	
	override def table = PersonaInstructionLinkDbModel.table
	
	override def targetId = personaId
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param targetId target id to assign to the new model (default = currently assigned value)
	  * @param instructionId instruction id to assign to the new model (default = currently assigned value)
	  * @param created created to assign to the new model (default = currently assigned value)
	  * @param removedAfter removed after to assign to the new model (default = currently assigned value)
	  */
	override def copyInstructionTargetingLink(id: Option[Int] = id, targetId: Option[Int] = targetId, 
		instructionId: Option[Int] = instructionId, created: Option[Instant] = created, 
		removedAfter: Option[Instant] = removedAfter) = 
		copy(id = id, personaId = targetId, instructionId = instructionId, created = created, 
			removedAfter = removedAfter)
	
	/**
	  * @param personaId Id of the persona to which the linked instruction applies
	  * @return A new copy of this model with the specified persona id
	  */
	override def withPersonaId(personaId: Int) = copy(personaId = Some(personaId))
}

