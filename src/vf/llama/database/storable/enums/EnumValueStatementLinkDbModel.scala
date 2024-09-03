package vf.llama.database.storable.enums

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.statement.StatementLinkDbProps
import vf.llama.database.storable.statement.{StatementLinkDbModel, StatementLinkDbModelFactoryLike, StatementLinkDbModelLike}
import vf.llama.model.factory.enums.EnumValueStatementLinkFactory
import vf.llama.model.partial.enums.EnumValueStatementLinkData
import vf.llama.model.stored.enums.EnumValueStatementLink

/**
  * 
	Used for constructing EnumValueStatementLinkDbModel instances and for inserting enum value statement links to
  *  the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object EnumValueStatementLinkDbModel 
	extends StatementLinkDbModelFactoryLike[EnumValueStatementLinkDbModel, EnumValueStatementLink, EnumValueStatementLinkData] 
		with EnumValueStatementLinkFactory[EnumValueStatementLinkDbModel] with StatementLinkDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with enum value version ids
	  */
	lazy val enumValueVersionId = property("enumValueVersionId")
	
	/**
	  * Database property used for interacting with statement ids
	  */
	override lazy val statementId = property("statementId")
	
	/**
	  * Database property used for interacting with order indices
	  */
	override lazy val orderIndex = property("orderIndex")
	
	
	// IMPLEMENTED	--------------------
	
	override def parentId = enumValueVersionId
	
	override def table = LlamaChatTables.enumValueStatementLink
	
	override def apply(data: EnumValueStatementLinkData) = 
		apply(None, Some(data.enumValueVersionId), Some(data.statementId), Some(data.orderIndex))
	
	/**
	  * @param enumValueVersionId Id of the enumeration value version where the linked statement appears
	  * @return A model containing only the specified enum value version id
	  */
	override def withEnumValueVersionId(enumValueVersionId: Int) = 
		apply(enumValueVersionId = Some(enumValueVersionId))
	
	override def withId(id: Int) = apply(id = Some(id))
	
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
	
	override protected def complete(id: Value, data: EnumValueStatementLinkData) = 
		EnumValueStatementLink(id.getInt, data)
}

/**
  * Used for interacting with EnumValueStatementLinks in the database
  * @param id enum value statement link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumValueStatementLinkDbModel(id: Option[Int] = None, enumValueVersionId: Option[Int] = None, 
	statementId: Option[Int] = None, orderIndex: Option[Int] = None) 
	extends StatementLinkDbModel with StatementLinkDbModelLike[EnumValueStatementLinkDbModel] 
		with EnumValueStatementLinkFactory[EnumValueStatementLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = EnumValueStatementLinkDbModel
	
	override def parentId = enumValueVersionId
	
	override def table = EnumValueStatementLinkDbModel.table
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param statementId statement id to assign to the new model (default = currently assigned value)
	  * @param parentId parent id to assign to the new model (default = currently assigned value)
	  * @param orderIndex order index to assign to the new model (default = currently assigned value)
	  */
	override def copyStatementLink(id: Option[Int] = id, statementId: Option[Int] = statementId, 
		parentId: Option[Int] = parentId, orderIndex: Option[Int] = orderIndex) = 
		copy(id = id, statementId = statementId, enumValueVersionId = parentId, orderIndex = orderIndex)
	
	/**
	  * @param enumValueVersionId Id of the enumeration value version where the linked statement appears
	  * @return A new copy of this model with the specified enum value version id
	  */
	override def withEnumValueVersionId(enumValueVersionId: Int) = 
		copy(enumValueVersionId = Some(enumValueVersionId))
}

