package vf.llama.database.storable.instruction

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.statement.StatementLinkDbProps
import vf.llama.database.storable.statement.{StatementLinkDbModel, StatementLinkDbModelFactoryLike, StatementLinkDbModelLike}
import vf.llama.model.factory.instruction.InstructionStatementLinkFactory
import vf.llama.model.partial.instruction.InstructionStatementLinkData
import vf.llama.model.stored.instruction.InstructionStatementLink

/**
  * 
	Used for constructing InstructionStatementLinkDbModel instances and for inserting instruction statement links
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object InstructionStatementLinkDbModel 
	extends StatementLinkDbModelFactoryLike[InstructionStatementLinkDbModel, InstructionStatementLink, InstructionStatementLinkData] 
		with InstructionStatementLinkFactory[InstructionStatementLinkDbModel] with StatementLinkDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with instruction version ids
	  */
	lazy val instructionVersionId = property("instructionVersionId")
	
	/**
	  * Database property used for interacting with statement ids
	  */
	override lazy val statementId = property("statementId")
	
	/**
	  * Database property used for interacting with order indices
	  */
	override lazy val orderIndex = property("orderIndex")
	
	
	// IMPLEMENTED	--------------------
	
	override def parentId = instructionVersionId
	
	override def table = LlamaChatTables.instructionStatementLink
	
	override def apply(data: InstructionStatementLinkData) = 
		apply(None, Some(data.instructionVersionId), Some(data.statementId), Some(data.orderIndex))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param instructionVersionId Id of the instruction version where the linked statement is made
	  * @return A model containing only the specified instruction version id
	  */
	override def withInstructionVersionId(instructionVersionId: Int) = 
		apply(instructionVersionId = Some(instructionVersionId))
	
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
	
	override protected def complete(id: Value, data: InstructionStatementLinkData) = 
		InstructionStatementLink(id.getInt, data)
}

/**
  * Used for interacting with InstructionStatementLinks in the database
  * @param id instruction statement link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class InstructionStatementLinkDbModel(id: Option[Int] = None, instructionVersionId: Option[Int] = None, 
	statementId: Option[Int] = None, orderIndex: Option[Int] = None) 
	extends StatementLinkDbModel with StatementLinkDbModelLike[InstructionStatementLinkDbModel] 
		with InstructionStatementLinkFactory[InstructionStatementLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = InstructionStatementLinkDbModel
	
	override def parentId = instructionVersionId
	
	override def table = InstructionStatementLinkDbModel.table
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param statementId statement id to assign to the new model (default = currently assigned value)
	  * @param parentId parent id to assign to the new model (default = currently assigned value)
	  * @param orderIndex order index to assign to the new model (default = currently assigned value)
	  */
	override def copyStatementLink(id: Option[Int] = id, statementId: Option[Int] = statementId, 
		parentId: Option[Int] = parentId, orderIndex: Option[Int] = orderIndex) = 
		copy(id = id, statementId = statementId, instructionVersionId = parentId, orderIndex = orderIndex)
	
	/**
	  * @param instructionVersionId Id of the instruction version where the linked statement is made
	  * @return A new copy of this model with the specified instruction version id
	  */
	override def withInstructionVersionId(instructionVersionId: Int) = 
		copy(instructionVersionId = Some(instructionVersionId))
}

