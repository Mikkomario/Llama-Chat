package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.{Empty, Single}
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import vf.llama.model.enumeration.SummaryStatementRole
import vf.llama.model.enumeration.SummaryStatementRole.Body
import vf.llama.model.factory.conversation.ConversationSummaryStatementLinkFactory
import vf.llama.model.partial.statement.{StatementLinkData, StatementLinkDataLike}

object ConversationSummaryStatementLinkData 
	extends FromModelFactoryWithSchema[ConversationSummaryStatementLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("summaryId", IntType, Vector("parentId", "parent_id", 
			"summary_id")), PropertyDeclaration("statementId", IntType, Single("statement_id")), 
			PropertyDeclaration("orderIndex", IntType, Single("order_index"), 0), PropertyDeclaration("role", 
			IntType, Empty, Body.id)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		ConversationSummaryStatementLinkData(valid("summaryId").getInt, valid("statementId").getInt, 
			valid("orderIndex").getInt, SummaryStatementRole.fromValue(valid("role")))
}

/**
  * Links statements to conversation summaries in which they appear
  * @param summaryId Id of the linked conversation summary
  * @param statementId Id of the statement made within the linked text / entity
  * @param orderIndex 0-based index that indicates the specific location of the placed text
  * @param role Role of the linked statement in the linked summary
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ConversationSummaryStatementLinkData(summaryId: Int, statementId: Int, orderIndex: Int = 0, 
	role: SummaryStatementRole = Body) 
	extends ConversationSummaryStatementLinkFactory[ConversationSummaryStatementLinkData] 
		with StatementLinkData with StatementLinkDataLike[ConversationSummaryStatementLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def parentId = summaryId
	
	override def toModel = super[StatementLinkData].toModel ++ Model(Single("role" -> role.id))
	
	override def copyStatementLink(statementId: Int, parentId: Int, orderIndex: Int) = 
		copy(summaryId = parentId, statementId = statementId, orderIndex = orderIndex)
	
	override def withRole(role: SummaryStatementRole) = copy(role = role)
	
	override def withSummaryId(summaryId: Int) = copy(summaryId = summaryId)
}

