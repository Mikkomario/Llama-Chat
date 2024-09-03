package vf.llama.model.stored.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.instruction.version.DbSingleInstructionVersion
import vf.llama.model.factory.instruction.InstructionVersionFactoryWrapper
import vf.llama.model.partial.instruction.InstructionVersionData

object InstructionVersion extends StoredFromModelFactory[InstructionVersionData, InstructionVersion]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = InstructionVersionData
	
	override protected def complete(model: AnyModel, data: InstructionVersionData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a instruction version that has already been stored in the database
  * @param id id of this instruction version in the database
  * @param data Wrapped instruction version data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class InstructionVersion(id: Int, data: InstructionVersionData) 
	extends StoredModelConvertible[InstructionVersionData] with FromIdFactory[Int, InstructionVersion] 
		with InstructionVersionFactoryWrapper[InstructionVersionData, InstructionVersion]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this instruction version in the database
	  */
	def access = DbSingleInstructionVersion(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: InstructionVersionData) = copy(data = data)
}

