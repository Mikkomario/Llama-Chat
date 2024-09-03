package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import vf.llama.model.factory.conversation.MemoryStatementLinkFactory
import vf.llama.model.partial.statement.{StatementLinkData, StatementLinkDataLike}

object MemoryStatementLinkData extends FromModelFactoryWithSchema[MemoryStatementLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("memoryId", IntType, Vector("memory_id", "parentId", 
			"parent_id")), PropertyDeclaration("statementId", IntType, Single("statement_id")), 
			PropertyDeclaration("orderIndex", IntType, Single("order_index"), 0)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		MemoryStatementLinkData(valid("memoryId").getInt, valid("statementId").getInt, 
			valid("orderIndex").getInt)
}

/**
  * Links statements to memories in which they appear
  * @param memoryId Id of the memory where the linked statement appears
  * @param statementId Id of the statement made within the linked text / entity
  * @param orderIndex 0-based index that indicates the specific location of the placed text
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MemoryStatementLinkData(memoryId: Int, statementId: Int, orderIndex: Int = 0) 
	extends MemoryStatementLinkFactory[MemoryStatementLinkData] with StatementLinkData 
		with StatementLinkDataLike[MemoryStatementLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def parentId = memoryId
	
	override def copyStatementLink(statementId: Int, parentId: Int, orderIndex: Int) = 
		copy(memoryId = parentId, statementId = statementId, orderIndex = orderIndex)
	
	override def withMemoryId(memoryId: Int) = copy(memoryId = memoryId)
}

