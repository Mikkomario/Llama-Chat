package vf.llama.database.access.many.instruction

import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.InstructionDbFactory
import vf.llama.database.storable.instruction.InstructionDbModel
import vf.llama.model.stored.instruction.Instruction

object ManyInstructionsAccess extends ViewFactory[ManyInstructionsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override
		 def apply(condition: Condition): ManyInstructionsAccess = _ManyInstructionsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyInstructionsAccess(override val accessCondition: Option[Condition]) 
		extends ManyInstructionsAccess
}

/**
  * A common trait for access points which target multiple instructions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyInstructionsAccess 
	extends ManyRowModelAccess[Instruction] with ChronoRowFactoryView[Instruction, ManyInstructionsAccess] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * creation times of the accessible instructions
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * Unique ids of the accessible instructions
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = InstructionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = InstructionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyInstructionsAccess = ManyInstructionsAccess(condition)
}

