package vf.llama.database.factory.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.immutable.Table
import utopia.vault.sql.OrderBy
import vf.llama.database.props.instruction.InstructionTargetingLinkDbProps
import vf.llama.model.partial.instruction.InstructionTargetingLinkData
import vf.llama.model.stored.instruction.InstructionTargetingLink

import java.time.Instant

object InstructionTargetingLinkDbFactory
{
	// OTHER	--------------------
	
	/**
	  * @param table Table from which data is read
	  * @param dbProps Database properties used when reading column data
	  * @return A factory used for parsing instruction targeting links from database model data
	  */
	def apply(table: Table, dbProps: InstructionTargetingLinkDbProps): InstructionTargetingLinkDbFactory = 
		_InstructionTargetingLinkDbFactory(table, dbProps)
	
	
	// NESTED	--------------------
	
	/**
	  * @param table Table from which data is read
	  * @param dbProps Database properties used when reading column data
	  */
	private case class _InstructionTargetingLinkDbFactory(table: Table, 
		dbProps: InstructionTargetingLinkDbProps) 
		extends InstructionTargetingLinkDbFactory
	{
		// IMPLEMENTED	--------------------
		
		override def defaultOrdering: Option[OrderBy] = None
		
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
			InstructionTargetingLink(id, InstructionTargetingLinkData(targetId, instructionId, created, 
				removedAfter))
	}
}

/**
  * Common trait for factories which parse instruction targeting link data from database-originated models
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkDbFactory 
	extends InstructionTargetingLinkDbFactoryLike[InstructionTargetingLink]

