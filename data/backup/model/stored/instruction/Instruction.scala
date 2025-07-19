package vf.llama.model.stored.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.instruction.DbSingleInstruction
import vf.llama.model.factory.instruction.InstructionFactoryWrapper
import vf.llama.model.partial.instruction.InstructionData

object Instruction extends StoredFromModelFactory[InstructionData, Instruction]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = InstructionData
	
	override protected def complete(model: AnyModel, data: InstructionData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a instruction that has already been stored in the database
  * @param id id of this instruction in the database
  * @param data Wrapped instruction data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Instruction(id: Int, data: InstructionData) 
	extends StoredModelConvertible[InstructionData] with FromIdFactory[Int, Instruction] 
		with InstructionFactoryWrapper[InstructionData, Instruction]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this instruction in the database
	  */
	def access = DbSingleInstruction(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: InstructionData) = copy(data = data)
}

