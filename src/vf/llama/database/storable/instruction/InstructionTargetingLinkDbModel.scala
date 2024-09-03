package vf.llama.database.storable.instruction

import utopia.vault.model.immutable.Table
import vf.llama.database.props.instruction.InstructionTargetingLinkDbProps

object InstructionTargetingLinkDbModel
{
	// OTHER	--------------------
	
	/**
	  * @param table The primarily targeted table
	  * @param props Targeted database properties
	  * 
		@return A factory used for constructing instruction targeting link models using the specified configuration
	  */
	def factory(table: Table, props: InstructionTargetingLinkDbProps) = 
		InstructionTargetingLinkDbModelFactory(table, props)
}

/**
  * Common trait for database interaction models dealing with instruction targeting links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkDbModel 
	extends InstructionTargetingLinkDbModelLike[InstructionTargetingLinkDbModel]

