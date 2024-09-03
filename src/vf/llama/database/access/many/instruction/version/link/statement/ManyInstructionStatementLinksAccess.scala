package vf.llama.database.access.many.instruction.version.link.statement

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.many.statement.ManyStatementLinksAccessLike
import vf.llama.database.factory.instruction.InstructionStatementLinkDbFactory
import vf.llama.database.storable.instruction.InstructionStatementLinkDbModel
import vf.llama.model.stored.instruction.InstructionStatementLink

object ManyInstructionStatementLinksAccess extends ViewFactory[ManyInstructionStatementLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyInstructionStatementLinksAccess = 
		_ManyInstructionStatementLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyInstructionStatementLinksAccess(override val accessCondition: Option[Condition]) 
		extends ManyInstructionStatementLinksAccess
}

/**
  * A common trait for access points which target multiple instruction statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyInstructionStatementLinksAccess 
	extends ManyStatementLinksAccessLike[InstructionStatementLink, ManyInstructionStatementLinksAccess] 
		with ManyRowModelAccess[InstructionStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * instruction version ids of the accessible instruction statement links
	  */
	def instructionVersionIds(implicit connection: Connection) = parentIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = InstructionStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = InstructionStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyInstructionStatementLinksAccess = 
		ManyInstructionStatementLinksAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param instructionVersionId instruction version id to target
	  * @return Copy of this access point that only includes instruction statement links 
	  * with the specified instruction version id
	  */
	def withinInstruction(instructionVersionId: Int) = 
		filter(model.instructionVersionId.column <=> instructionVersionId)
	
	/**
	  * @param instructionVersionIds Targeted instruction version ids
	  * 
		@return Copy of this access point that only includes instruction statement links where instruction version
	  *  id is within the specified value set
	  */
	def withinInstructions(instructionVersionIds: IterableOnce[Int]) = 
		filter(model.instructionVersionId.column.in(IntSet.from(instructionVersionIds)))
}

