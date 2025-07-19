package vf.llama.database.storable.conversation

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.statement.StatementLinkDbProps
import vf.llama.database.storable.statement.{StatementLinkDbModel, StatementLinkDbModelFactoryLike, StatementLinkDbModelLike}
import vf.llama.model.enumeration.SummaryStatementRole
import vf.llama.model.factory.conversation.ConversationSummaryStatementLinkFactory
import vf.llama.model.partial.conversation.ConversationSummaryStatementLinkData
import vf.llama.model.stored.conversation.ConversationSummaryStatementLink

/**
  * Used for constructing ConversationSummaryStatementLinkDbModel instances and for inserting conversation
  *  summary statement links to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ConversationSummaryStatementLinkDbModel 
	extends StatementLinkDbModelFactoryLike[ConversationSummaryStatementLinkDbModel, ConversationSummaryStatementLink, ConversationSummaryStatementLinkData] 
		with ConversationSummaryStatementLinkFactory[ConversationSummaryStatementLinkDbModel] 
		with StatementLinkDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with summary ids
	  */
	lazy val summaryId = property("summaryId")
	
	/**
	  * Database property used for interacting with statement ids
	  */
	override lazy val statementId = property("statementId")
	
	/**
	  * Database property used for interacting with order indices
	  */
	override lazy val orderIndex = property("orderIndex")
	
	/**
	  * Database property used for interacting with roles
	  */
	lazy val role = property("roleId")
	
	
	// IMPLEMENTED	--------------------
	
	override def parentId = summaryId
	
	override def table = LlamaChatTables.conversationSummaryStatementLink
	
	override def apply(data: ConversationSummaryStatementLinkData) = 
		apply(None, Some(data.summaryId), Some(data.statementId), Some(data.orderIndex), Some(data.role))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param orderIndex 0-based index that indicates the specific location of the placed text
	  * @return A model containing only the specified order index
	  */
	override def withOrderIndex(orderIndex: Int) = apply(orderIndex = Some(orderIndex))
	
	/**
	  * @param role Role of the linked statement in the linked summary
	  * @return A model containing only the specified role
	  */
	override def withRole(role: SummaryStatementRole) = apply(role = Some(role))
	
	/**
	  * @param statementId Id of the statement made within the linked text / entity
	  * @return A model containing only the specified statement id
	  */
	override def withStatementId(statementId: Int) = apply(statementId = Some(statementId))
	
	/**
	  * @param summaryId Id of the linked conversation summary
	  * @return A model containing only the specified summary id
	  */
	override def withSummaryId(summaryId: Int) = apply(summaryId = Some(summaryId))
	
	override protected def complete(id: Value, data: ConversationSummaryStatementLinkData) = 
		ConversationSummaryStatementLink(id.getInt, data)
}

/**
  * Used for interacting with ConversationSummaryStatementLinks in the database
  * @param id conversation summary statement link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ConversationSummaryStatementLinkDbModel(id: Option[Int] = None, summaryId: Option[Int] = None,
                                                   statementId: Option[Int] = None, orderIndex: Option[Int] = None,
                                                   role: Option[SummaryStatementRole] = None)
	extends StatementLinkDbModel with StatementLinkDbModelLike[ConversationSummaryStatementLinkDbModel] 
		with ConversationSummaryStatementLinkFactory[ConversationSummaryStatementLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = ConversationSummaryStatementLinkDbModel
	
	override def parentId = summaryId
	
	override def table = ConversationSummaryStatementLinkDbModel.table
	
	override def valueProperties = 
		super[StatementLinkDbModelLike].valueProperties ++
			Single(ConversationSummaryStatementLinkDbModel.role.name -> role.map[Value] { e => e.id }.getOrElse(Value.empty))
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param statementId statement id to assign to the new model (default = currently assigned value)
	  * @param parentId parent id to assign to the new model (default = currently assigned value)
	  * @param orderIndex order index to assign to the new model (default = currently assigned value)
	  */
	override def copyStatementLink(id: Option[Int] = id, statementId: Option[Int] = statementId, 
		parentId: Option[Int] = parentId, orderIndex: Option[Int] = orderIndex) = 
		copy(id = id, statementId = statementId, summaryId = parentId, orderIndex = orderIndex)
	
	/**
	  * @param role Role of the linked statement in the linked summary
	  * @return A new copy of this model with the specified role
	  */
	override def withRole(role: SummaryStatementRole) = copy(role = Some(role))
	
	/**
	  * @param summaryId Id of the linked conversation summary
	  * @return A new copy of this model with the specified summary id
	  */
	override def withSummaryId(summaryId: Int) = copy(summaryId = Some(summaryId))
}

