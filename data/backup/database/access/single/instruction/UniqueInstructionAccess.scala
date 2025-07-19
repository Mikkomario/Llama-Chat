package vf.llama.database.access.single.instruction

import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{FilterableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.InstructionDbFactory
import vf.llama.database.storable.instruction.InstructionDbModel
import vf.llama.model.stored.instruction.Instruction

object UniqueInstructionAccess extends ViewFactory[UniqueInstructionAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override
		 def apply(condition: Condition): UniqueInstructionAccess = _UniqueInstructionAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueInstructionAccess(override val accessCondition: Option[Condition]) 
		extends UniqueInstructionAccess
}

/**
  * A common trait for access points that return individual and distinct instructions.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueInstructionAccess 
	extends SingleChronoRowModelAccess[Instruction, UniqueInstructionAccess] 
		with DistinctModelAccess[Instruction, Option[Instruction], Value] 
		with FilterableView[UniqueInstructionAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Time when this instruction was added to the database. 
	  * None if no instruction (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Unique id of the accessible instruction. None if no instruction was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = InstructionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = InstructionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueInstructionAccess = UniqueInstructionAccess(condition)
}

