package vf.llama.database.storable.statement

import utopia.logos.database.storable.text.TextPlacementDbModelLike
import vf.llama.database.props.statement.StatementLinkDbProps
import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for database models used for interacting with statement link data in the database
  * @tparam Repr Type of this DB model
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkDbModelLike[+Repr] extends TextPlacementDbModelLike[Repr] with StatementLinkFactory[Repr]
{
	// ABSTRACT	--------------------
	
	def statementId: Option[Int]
	
	/**
	  * Access to the database properties which are utilized in this model
	  */
	override def dbProps: StatementLinkDbProps
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param parentId parent id to assign to the new model (default = currently assigned value)
	  * @param statementId statement id to assign to the new model (default = currently assigned value)
	  * @param orderIndex order index to assign to the new model (default = currently assigned value)
	  * @return Copy of this model with the specified statement link properties
	  */
	protected def copyStatementLink(id: Option[Int] = id, parentId: Option[Int] = parentId, 
		statementId: Option[Int] = statementId, orderIndex: Option[Int] = orderIndex): Repr
	
	
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
		copyStatementLink(id = id, parentId = parentId, statementId = placedId, orderIndex = orderIndex)
	
	override def withPlacedId(placedId: Int) = withStatementId(placedId)
	
	/**
	  * @param statementId Id of the statement made within the linked text / entity
	  * @return A new copy of this model with the specified statement id
	  */
	override def withStatementId(statementId: Int) = copyStatementLink(statementId = Some(statementId))
}

