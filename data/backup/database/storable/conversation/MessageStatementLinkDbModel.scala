package vf.llama.database.storable.conversation

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.statement.StatementLinkDbProps
import vf.llama.database.storable.statement.{StatementLinkDbModel, StatementLinkDbModelFactoryLike, StatementLinkDbModelLike}
import vf.llama.model.factory.conversation.MessageStatementLinkFactory
import vf.llama.model.partial.conversation.MessageStatementLinkData
import vf.llama.model.stored.conversation.MessageStatementLink

/**
  * Used for constructing MessageStatementLinkDbModel instances and for inserting message statement links
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object MessageStatementLinkDbModel 
	extends StatementLinkDbModelFactoryLike[MessageStatementLinkDbModel, MessageStatementLink, MessageStatementLinkData] 
		with MessageStatementLinkFactory[MessageStatementLinkDbModel] with StatementLinkDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with message ids
	  */
	lazy val messageId = property("messageId")
	
	/**
	  * Database property used for interacting with statement ids
	  */
	override lazy val statementId = property("statementId")
	
	/**
	  * Database property used for interacting with order indices
	  */
	override lazy val orderIndex = property("orderIndex")
	
	
	// IMPLEMENTED	--------------------
	
	override def parentId = messageId
	
	override def table = LlamaChatTables.messageStatementLink
	
	override def apply(data: MessageStatementLinkData) = 
		apply(None, Some(data.messageId), Some(data.statementId), Some(data.orderIndex))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param messageId Id of the message where the linked statement appears
	  * @return A model containing only the specified message id
	  */
	override def withMessageId(messageId: Int) = apply(messageId = Some(messageId))
	
	/**
	  * @param orderIndex 0-based index that indicates the specific location of the placed text
	  * @return A model containing only the specified order index
	  */
	override def withOrderIndex(orderIndex: Int) = apply(orderIndex = Some(orderIndex))
	
	/**
	  * @param statementId Id of the statement made within the linked text / entity
	  * @return A model containing only the specified statement id
	  */
	override def withStatementId(statementId: Int) = apply(statementId = Some(statementId))
	
	override protected def complete(id: Value, data: MessageStatementLinkData) = 
		MessageStatementLink(id.getInt, data)
}

/**
  * Used for interacting with MessageStatementLinks in the database
  * @param id message statement link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MessageStatementLinkDbModel(id: Option[Int] = None, messageId: Option[Int] = None, 
	statementId: Option[Int] = None, orderIndex: Option[Int] = None) 
	extends StatementLinkDbModel with StatementLinkDbModelLike[MessageStatementLinkDbModel] 
		with MessageStatementLinkFactory[MessageStatementLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = MessageStatementLinkDbModel
	
	override def parentId = messageId
	
	override def table = MessageStatementLinkDbModel.table
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param statementId statement id to assign to the new model (default = currently assigned value)
	  * @param parentId parent id to assign to the new model (default = currently assigned value)
	  * @param orderIndex order index to assign to the new model (default = currently assigned value)
	  */
	override def copyStatementLink(id: Option[Int] = id, statementId: Option[Int] = statementId, 
		parentId: Option[Int] = parentId, orderIndex: Option[Int] = orderIndex) = 
		copy(id = id, statementId = statementId, messageId = parentId, orderIndex = orderIndex)
	
	/**
	  * @param messageId Id of the message where the linked statement appears
	  * @return A new copy of this model with the specified message id
	  */
	override def withMessageId(messageId: Int) = copy(messageId = Some(messageId))
}

