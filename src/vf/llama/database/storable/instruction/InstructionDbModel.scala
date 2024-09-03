package vf.llama.database.storable.instruction

import utopia.flow.collection.immutable.Pair
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.instruction.InstructionFactory
import vf.llama.model.partial.instruction.InstructionData
import vf.llama.model.stored.instruction.Instruction

import java.time.Instant

/**
  * Used for constructing InstructionDbModel instances and for inserting instructions to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object InstructionDbModel 
	extends StorableFactory[InstructionDbModel, Instruction, InstructionData] 
		with FromIdFactory[Int, InstructionDbModel] with HasIdProperty 
		with InstructionFactory[InstructionDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.instruction
	
	override def apply(data: InstructionData) = apply(None, Some(data.created))
	
	/**
	  * @param created Time when this instruction was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	override protected def complete(id: Value, data: InstructionData) = Instruction(id.getInt, data)
}

/**
  * Used for interacting with Instructions in the database
  * @param id instruction database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class InstructionDbModel(id: Option[Int] = None, created: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, InstructionDbModel] 
		with InstructionFactory[InstructionDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = InstructionDbModel.table
	
	override def valueProperties = 
		Pair(InstructionDbModel.id.name -> id, InstructionDbModel.created.name -> created)
	
	/**
	  * @param created Time when this instruction was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	override def withId(id: Int) = copy(id = Some(id))
}

