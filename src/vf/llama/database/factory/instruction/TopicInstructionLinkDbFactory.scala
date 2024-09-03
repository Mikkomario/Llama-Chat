package vf.llama.database.factory.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.instruction.TopicInstructionLinkDbModel
import vf.llama.model.partial.instruction.TopicInstructionLinkData
import vf.llama.model.stored.instruction.TopicInstructionLink

import java.time.Instant

/**
  * Used for reading topic instruction link data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object TopicInstructionLinkDbFactory 
	extends InstructionTargetingLinkDbFactoryLike[TopicInstructionLink] 
		with FromTimelineRowFactory[TopicInstructionLink] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	override def dbProps = TopicInstructionLinkDbModel
	
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
		TopicInstructionLink(id, TopicInstructionLinkData(targetId, instructionId, created, removedAfter))
}

