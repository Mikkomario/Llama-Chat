package vf.llama.model.partial.instruction

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import vf.llama.model.factory.instruction.InstructionStatementLinkFactory
import vf.llama.model.partial.statement.{StatementLinkData, StatementLinkDataLike}

object InstructionStatementLinkData extends FromModelFactoryWithSchema[InstructionStatementLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("instructionVersionId", IntType, 
			Vector("instruction_version_id", "parentId", "parent_id")), PropertyDeclaration("statementId", 
			IntType, Single("statement_id")), PropertyDeclaration("orderIndex", IntType, 
			Single("order_index"), 0)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		InstructionStatementLinkData(valid("instructionVersionId").getInt, valid("statementId").getInt, 
			valid("orderIndex").getInt)
}

/**
  * Attaches a statement to an instruction where that statement appears
  * @param instructionVersionId Id of the instruction version where the linked statement is made
  * @param statementId Id of the statement made within the linked text / entity
  * @param orderIndex 0-based index that indicates the specific location of the placed text
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class InstructionStatementLinkData(instructionVersionId: Int, statementId: Int, orderIndex: Int = 0) 
	extends InstructionStatementLinkFactory[InstructionStatementLinkData] with StatementLinkData 
		with StatementLinkDataLike[InstructionStatementLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def parentId = instructionVersionId
	
	override def copyStatementLink(statementId: Int, parentId: Int, orderIndex: Int) = 
		copy(instructionVersionId = parentId, statementId = statementId, orderIndex = orderIndex)
	
	override def withInstructionVersionId(instructionVersionId: Int) = 
		copy(instructionVersionId = instructionVersionId)
}

