package vf.llama.database.factory.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.instruction.PersonaInstructionLinkDbModel
import vf.llama.model.partial.instruction.PersonaInstructionLinkData
import vf.llama.model.stored.instruction.PersonaInstructionLink

import java.time.Instant

/**
  * Used for reading persona instruction link data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaInstructionLinkDbFactory 
	extends InstructionTargetingLinkDbFactoryLike[PersonaInstructionLink] 
		with FromTimelineRowFactory[PersonaInstructionLink] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	override def dbProps = PersonaInstructionLinkDbModel
	
	override def nonDeprecatedCondition = dbProps.nonDeprecatedCondition
	
	override def table = dbProps.table
	
	override def timestamp = dbProps.created
	
	/**
	  * @param model Model from which additional data may be read
	  * @param id Id to assign to the read/parsed instruction targeting link
	  * @param targetId target id to assign to the new instruction targeting link
	  * @param instructionId instruction id to assign to the new instruction targeting link
	  * @param created created to assign to the new instruction targeting link
	  * @param removedAfter removed after to assign to the new instruction targeting link
	  */
	override protected def apply(model: AnyModel, id: Int, targetId: Int, instructionId: Int, 
		created: Instant, removedAfter: Option[Instant]) = 
		PersonaInstructionLink(id, PersonaInstructionLinkData(targetId, instructionId, created, removedAfter))
}

