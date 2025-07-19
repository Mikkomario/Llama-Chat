package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import vf.llama.model.factory.conversation.SessionSummaryStatementLinkFactory
import vf.llama.model.partial.statement.{StatementLinkData, StatementLinkDataLike}

object SessionSummaryStatementLinkData extends FromModelFactoryWithSchema[SessionSummaryStatementLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("sessionId", IntType, Vector("parentId", "parent_id", 
			"session_id")), PropertyDeclaration("statementId", IntType, Single("statement_id")), 
			PropertyDeclaration("orderIndex", IntType, Single("order_index"), 0)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		SessionSummaryStatementLinkData(valid("sessionId").getInt, valid("statementId").getInt, 
			valid("orderIndex").getInt)
}

/**
  * Links statements to session summaries in which they appear
  * @param sessionId Id of the session being summarized
  * @param statementId Id of the statement made within the linked text / entity
  * @param orderIndex 0-based index that indicates the specific location of the placed text
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class SessionSummaryStatementLinkData(sessionId: Int, statementId: Int, orderIndex: Int = 0) 
	extends SessionSummaryStatementLinkFactory[SessionSummaryStatementLinkData] with StatementLinkData 
		with StatementLinkDataLike[SessionSummaryStatementLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def parentId = sessionId
	
	override def copyStatementLink(statementId: Int, parentId: Int, orderIndex: Int) = 
		copy(sessionId = parentId, statementId = statementId, orderIndex = orderIndex)
	
	override def withSessionId(sessionId: Int) = copy(sessionId = sessionId)
}

