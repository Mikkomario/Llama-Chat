package vf.llama.database.storable.conversation

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.statement.StatementLinkDbProps
import vf.llama.database.storable.statement.{StatementLinkDbModel, StatementLinkDbModelFactoryLike, StatementLinkDbModelLike}
import vf.llama.model.factory.conversation.MemoryStatementLinkFactory
import vf.llama.model.partial.conversation.MemoryStatementLinkData
import vf.llama.model.stored.conversation.MemoryStatementLink

/**
  * Used for constructing MemoryStatementLinkDbModel instances and for inserting memory statement links
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object MemoryStatementLinkDbModel 
	extends StatementLinkDbModelFactoryLike[MemoryStatementLinkDbModel, MemoryStatementLink, MemoryStatementLinkData] 
		with MemoryStatementLinkFactory[MemoryStatementLinkDbModel] with StatementLinkDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with memory ids
	  */
	lazy val memoryId = property("memoryId")
	
	/**
	  * Database property used for interacting with statement ids
	  */
	override lazy val statementId = property("statementId")
	
	/**
	  * Database property used for interacting with order indices
	  */
	override lazy val orderIndex = property("orderIndex")
	
	
	// IMPLEMENTED	--------------------
	
	override def parentId = memoryId
	
	override def table = LlamaChatTables.memoryStatementLink
	
	override def apply(data: MemoryStatementLinkData) = 
		apply(None, Some(data.memoryId), Some(data.statementId), Some(data.orderIndex))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param memoryId Id of the memory where the linked statement appears
	  * @return A model containing only the specified memory id
	  */
	override def withMemoryId(memoryId: Int) = apply(memoryId = Some(memoryId))
	
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
	
	override protected def complete(id: Value, data: MemoryStatementLinkData) = 
		MemoryStatementLink(id.getInt, data)
}

/**
  * Used for interacting with MemoryStatementLinks in the database
  * @param id memory statement link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MemoryStatementLinkDbModel(id: Option[Int] = None, memoryId: Option[Int] = None, 
	statementId: Option[Int] = None, orderIndex: Option[Int] = None) 
	extends StatementLinkDbModel with StatementLinkDbModelLike[MemoryStatementLinkDbModel] 
		with MemoryStatementLinkFactory[MemoryStatementLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = MemoryStatementLinkDbModel
	
	override def parentId = memoryId
	
	override def table = MemoryStatementLinkDbModel.table
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param statementId statement id to assign to the new model (default = currently assigned value)
	  * @param parentId parent id to assign to the new model (default = currently assigned value)
	  * @param orderIndex order index to assign to the new model (default = currently assigned value)
	  */
	override def copyStatementLink(id: Option[Int] = id, statementId: Option[Int] = statementId, 
		parentId: Option[Int] = parentId, orderIndex: Option[Int] = orderIndex) = 
		copy(id = id, statementId = statementId, memoryId = parentId, orderIndex = orderIndex)
	
	/**
	  * @param memoryId Id of the memory where the linked statement appears
	  * @return A new copy of this model with the specified memory id
	  */
	override def withMemoryId(memoryId: Int) = copy(memoryId = Some(memoryId))
}

