package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import vf.llama.model.factory.conversation.MessageStatementLinkFactory
import vf.llama.model.partial.statement.{StatementLinkData, StatementLinkDataLike}

object MessageStatementLinkData extends FromModelFactoryWithSchema[MessageStatementLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("messageId", IntType, Vector("message_id", "parentId", 
			"parent_id")), PropertyDeclaration("statementId", IntType, Single("statement_id")), 
			PropertyDeclaration("orderIndex", IntType, Single("order_index"), 0)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		MessageStatementLinkData(valid("messageId").getInt, valid("statementId").getInt, 
			valid("orderIndex").getInt)
}

/**
  * Links statements to messages where they appear
  * @param messageId Id of the message where the linked statement appears
  * @param statementId Id of the statement made within the linked text / entity
  * @param orderIndex 0-based index that indicates the specific location of the placed text
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MessageStatementLinkData(messageId: Int, statementId: Int, orderIndex: Int = 0) 
	extends MessageStatementLinkFactory[MessageStatementLinkData] with StatementLinkData 
		with StatementLinkDataLike[MessageStatementLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def parentId = messageId
	
	override def copyStatementLink(statementId: Int, parentId: Int, orderIndex: Int) = 
		copy(messageId = parentId, statementId = statementId, orderIndex = orderIndex)
	
	override def withMessageId(messageId: Int) = copy(messageId = messageId)
}

