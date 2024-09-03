package vf.llama.database.access.single.instruction.version.link.statement

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.statement.UniqueStatementLinkAccessLike
import vf.llama.database.factory.instruction.InstructionStatementLinkDbFactory
import vf.llama.database.storable.instruction.InstructionStatementLinkDbModel
import vf.llama.model.stored.instruction.InstructionStatementLink

object UniqueInstructionStatementLinkAccess extends ViewFactory[UniqueInstructionStatementLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueInstructionStatementLinkAccess = 
		_UniqueInstructionStatementLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueInstructionStatementLinkAccess(override val accessCondition: Option[Condition]) 
		extends UniqueInstructionStatementLinkAccess
}

/**
  * A common trait for access points that return individual and distinct instruction statement links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueInstructionStatementLinkAccess 
	extends UniqueStatementLinkAccessLike[InstructionStatementLink, UniqueInstructionStatementLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the instruction version where the linked statement is made. 
	  * None if no instruction statement link (or value) was found.
	  */
	def instructionVersionId(implicit connection: Connection) = parentId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = InstructionStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = InstructionStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueInstructionStatementLinkAccess = 
		UniqueInstructionStatementLinkAccess(condition)
}

