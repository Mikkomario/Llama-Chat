package vf.llama.database.factory.instruction

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.llama.database.storable.instruction.InstructionDbModel
import vf.llama.model.partial.instruction.InstructionData
import vf.llama.model.stored.instruction.Instruction

/**
  * Used for reading instruction data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object InstructionDbFactory 
	extends FromValidatedRowModelFactory[Instruction] with FromTimelineRowFactory[Instruction]
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = InstructionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		Instruction(valid(this.model.id.name).getInt, 
			InstructionData(valid(this.model.created.name).getInstant))
}

