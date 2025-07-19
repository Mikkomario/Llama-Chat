package vf.llama.database.storable.conversation

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.statement.StatementLinkDbProps
import vf.llama.database.storable.statement.{StatementLinkDbModel, StatementLinkDbModelFactoryLike, StatementLinkDbModelLike}
import vf.llama.model.factory.conversation.SessionSummaryStatementLinkFactory
import vf.llama.model.partial.conversation.SessionSummaryStatementLinkData
import vf.llama.model.stored.conversation.SessionSummaryStatementLink

/**
  * 
	Used for constructing SessionSummaryStatementLinkDbModel instances and for inserting session summary statement
  *  links to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object SessionSummaryStatementLinkDbModel 
	extends StatementLinkDbModelFactoryLike[SessionSummaryStatementLinkDbModel, SessionSummaryStatementLink, SessionSummaryStatementLinkData] 
		with SessionSummaryStatementLinkFactory[SessionSummaryStatementLinkDbModel] with StatementLinkDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with session ids
	  */
	lazy val sessionId = property("sessionId")
	
	/**
	  * Database property used for interacting with statement ids
	  */
	override lazy val statementId = property("statementId")
	
	/**
	  * Database property used for interacting with order indices
	  */
	override lazy val orderIndex = property("orderIndex")
	
	
	// IMPLEMENTED	--------------------
	
	override def parentId = sessionId
	
	override def table = LlamaChatTables.sessionSummaryStatementLink
	
	override def apply(data: SessionSummaryStatementLinkData) = 
		apply(None, Some(data.sessionId), Some(data.statementId), Some(data.orderIndex))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param orderIndex 0-based index that indicates the specific location of the placed text
	  * @return A model containing only the specified order index
	  */
	override def withOrderIndex(orderIndex: Int) = apply(orderIndex = Some(orderIndex))
	
	/**
	  * @param sessionId Id of the session being summarized
	  * @return A model containing only the specified session id
	  */
	override def withSessionId(sessionId: Int) = apply(sessionId = Some(sessionId))
	
	/**
	  * @param statementId Id of the statement made within the linked text / entity
	  * @return A model containing only the specified statement id
	  */
	override def withStatementId(statementId: Int) = apply(statementId = Some(statementId))
	
	override protected def complete(id: Value, data: SessionSummaryStatementLinkData) = 
		SessionSummaryStatementLink(id.getInt, data)
}

/**
  * Used for interacting with SessionSummaryStatementLinks in the database
  * @param id session summary statement link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class SessionSummaryStatementLinkDbModel(id: Option[Int] = None, sessionId: Option[Int] = None, 
	statementId: Option[Int] = None, orderIndex: Option[Int] = None) 
	extends StatementLinkDbModel with StatementLinkDbModelLike[SessionSummaryStatementLinkDbModel] 
		with SessionSummaryStatementLinkFactory[SessionSummaryStatementLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = SessionSummaryStatementLinkDbModel
	
	override def parentId = sessionId
	
	override def table = SessionSummaryStatementLinkDbModel.table
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param statementId statement id to assign to the new model (default = currently assigned value)
	  * @param parentId parent id to assign to the new model (default = currently assigned value)
	  * @param orderIndex order index to assign to the new model (default = currently assigned value)
	  */
	override def copyStatementLink(id: Option[Int] = id, statementId: Option[Int] = statementId, 
		parentId: Option[Int] = parentId, orderIndex: Option[Int] = orderIndex) = 
		copy(id = id, statementId = statementId, sessionId = parentId, orderIndex = orderIndex)
	
	/**
	  * @param sessionId Id of the session being summarized
	  * @return A new copy of this model with the specified session id
	  */
	override def withSessionId(sessionId: Int) = copy(sessionId = Some(sessionId))
}

