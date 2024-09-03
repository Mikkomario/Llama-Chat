package vf.llama.database.factory.instruction

import utopia.flow.generic.model.immutable.Model
import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.props.instruction.InstructionTargetingLinkDbProps

import java.time.Instant

/**
  * Common trait for factories which parse instruction targeting link data from database-originated models
  * @tparam A Type of read instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkDbFactoryLike[+A] 
	extends FromValidatedRowModelFactory[A] with FromTimelineRowFactory[A] with Deprecatable
{
	// ABSTRACT	--------------------
	
	/**
	  * Database properties used when parsing column data
	  */
	def dbProps: InstructionTargetingLinkDbProps
	
	/**
	  * @param model Model from which additional data may be read
	  * @param id Id to assign to the read/parsed instruction targeting link
	  * @param targetId target id to assign to the new instruction targeting link
	  * @param instructionId instruction id to assign to the new instruction targeting link
	  * @param created created to assign to the new instruction targeting link
	  * @param removedAfter removed after to assign to the new instruction targeting link
	  * @return A instruction targeting link with the specified data
	  */
	protected def apply(model: AnyModel, id: Int, targetId: Int, instructionId: Int, created: Instant, 
		removedAfter: Option[Instant]): A
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		apply(valid, valid(dbProps.id.name).getInt, valid(dbProps.targetId.name).getInt, 
			valid(dbProps.instructionId.name).getInt, valid(dbProps.created.name).getInstant, 
			valid(dbProps.removedAfter.name).instant)
}

