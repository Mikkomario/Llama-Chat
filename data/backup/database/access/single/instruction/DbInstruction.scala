package vf.llama.database.access.single.instruction

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.InstructionDbFactory
import vf.llama.database.storable.instruction.InstructionDbModel
import vf.llama.model.stored.instruction.Instruction

/**
  * Used for accessing individual instructions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbInstruction extends SingleRowModelAccess[Instruction] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = InstructionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = InstructionDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted instruction
	  * @return An access point to that instruction
	  */
	def apply(id: Int) = DbSingleInstruction(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique instructions.
	  * @return An access point to the instruction that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueInstructionAccess(condition)
}

