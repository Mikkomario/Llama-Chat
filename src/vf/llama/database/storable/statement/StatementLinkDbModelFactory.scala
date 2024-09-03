package vf.llama.database.storable.statement

import utopia.flow.generic.model.immutable.Value
import utopia.logos.database.storable.text.TextPlacementDbModelFactory
import utopia.vault.model.immutable.{DbPropertyDeclaration, Table}
import vf.llama.database.props.statement.{StatementLinkDbProps, StatementLinkDbPropsWrapper}
import vf.llama.model.partial.statement.StatementLinkData
import vf.llama.model.stored.statement.StatementLink

object StatementLinkDbModelFactory
{
	// OTHER	--------------------
	
	/**
	  * @return A factory for constructing statement link database models
	  */
	def apply(table: Table, dbProps: StatementLinkDbProps) = StatementLinkDbModelFactoryImpl(table, dbProps)
	
	
	// NESTED	--------------------
	
	/**
	  * Used for constructing StatementLinkDbModel instances and for inserting statement links to the database
	  * @param table Table targeted by these models
	  * @param statementLinkDbProps Properties which specify how the database interactions are performed
	  * @author Mikko Hilpinen
	  * @since 01.09.2024, v0.1
	  */
	case class StatementLinkDbModelFactoryImpl(table: Table, statementLinkDbProps: StatementLinkDbProps) 
		extends StatementLinkDbModelFactory with StatementLinkDbPropsWrapper
	{
		// ATTRIBUTES	--------------------
		
		override lazy val id = DbPropertyDeclaration("id", index)
		
		
		// IMPLEMENTED	--------------------
		
		override def apply(data: StatementLinkData): StatementLinkDbModel =
			apply(None, Some(data.parentId), Some(data.statementId), Some(data.orderIndex))
		
		override def withId(id: Int) = apply(id = Some(id))
		
		/**
		  * @param orderIndex 0-based index that indicates the specific location of the placed text
		  * @return A model containing only the specified order index
		  */
		override def withOrderIndex(orderIndex: Int) = apply(orderIndex = Some(orderIndex))
		
		/**
		  * @param parentId Id of the text where the placed text appears
		  * @return A model containing only the specified parent id
		  */
		override def withParentId(parentId: Int) = apply(parentId = Some(parentId))
		
		/**
		  * @param statementId Id of the statement made within the linked text / entity
		  * @return A model containing only the specified statement id
		  */
		override def withStatementId(statementId: Int) = apply(statementId = Some(statementId))
		
		override protected def complete(id: Value, data: StatementLinkData) = StatementLink(id.getInt, data)
		
		
		// OTHER	--------------------
		
		/**
		  * @param id statement link database id
		  * @return Constructs a new statement link database model with the specified properties
		  */
		def apply(id: Option[Int] = None, parentId: Option[Int] = None, statementId: Option[Int] = None, 
			orderIndex: Option[Int] = None): StatementLinkDbModel = 
			_StatementLinkDbModel(table, statementLinkDbProps, id, parentId, statementId, orderIndex)
	}
	
	/**
	  * Used for interacting with StatementLinks in the database
	  * @param table Table interacted with when using this model
	  * @param dbProps Configurations of the interacted database properties
	  * @param id statement link database id
	  * @author Mikko Hilpinen
	  * @since 01.09.2024, v0.1
	  */
	private case class _StatementLinkDbModel(table: Table, dbProps: StatementLinkDbProps, 
		id: Option[Int] = None, parentId: Option[Int] = None, statementId: Option[Int] = None, 
		orderIndex: Option[Int] = None) 
		extends StatementLinkDbModel
	{
		// IMPLEMENTED	--------------------
		
		override def placedId = statementId
		
		/**
		  * @param id Id to assign to the new model (default = currently assigned id)
		  * @param parentId parent id to assign to the new model (default = currently assigned value)
		  * @param placedId placed id to assign to the new model (default = currently assigned value)
		  * @param orderIndex order index to assign to the new model (default = currently assigned value)
		  */
		override def copyTextPlacement(id: Option[Int] = id, parentId: Option[Int] = parentId, 
			placedId: Option[Int] = placedId, orderIndex: Option[Int] = orderIndex) = 
			copy(id = id, parentId = parentId, statementId = placedId, orderIndex = orderIndex)
		
		/**
		  * @param id Id to assign to the new model (default = currently assigned id)
		  * @param parentId parent id to assign to the new model (default = currently assigned value)
		  * @param statementId statement id to assign to the new model (default = currently assigned value)
		  * @param orderIndex order index to assign to the new model (default = currently assigned value)
		  * @return Copy of this model with the specified statement link properties
		  */
		override protected def copyStatementLink(id: Option[Int] = id, parentId: Option[Int] = parentId, 
			statementId: Option[Int] = statementId, orderIndex: Option[Int] = orderIndex) = 
			copy(id = id, parentId = parentId, statementId = statementId, orderIndex = orderIndex)
	}
}

/**
  * Common trait for factories yielding statement link database models
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkDbModelFactory 
	extends StatementLinkDbModelFactoryLike[StatementLinkDbModel, StatementLink, StatementLinkData] 
		with TextPlacementDbModelFactory

