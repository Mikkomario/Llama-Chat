package vf.llama.model.partial.statement

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.logos.model.partial.text.{TextPlacementData, TextPlacementDataLike}

object StatementLinkData extends FromModelFactoryWithSchema[StatementLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("parentId", IntType, Single("parent_id")), 
			PropertyDeclaration("statementId", IntType, Vector("placedId", "placed_id", "statement_id")), 
			PropertyDeclaration("orderIndex", IntType, Single("order_index"), 0)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		StatementLinkData(valid("parentId").getInt, valid("statementId").getInt, valid("orderIndex").getInt)
	
	
	// OTHER	--------------------
	
	/**
	  * Creates a new statement link
	  * @param parentId Id of the text where the placed text appears
	  * @param statementId Id of the statement made within the linked text / entity
	  * @param orderIndex 0-based index that indicates the specific location of the placed text
	  * @return statement link with the specified properties
	  */
	def apply(parentId: Int, statementId: Int, orderIndex: Int = 0): StatementLinkData = 
		_StatementLinkData(parentId, statementId, orderIndex)
	
	
	// NESTED	--------------------
	
	/**
	  * Concrete implementation of the statement link data trait
	  * @param parentId Id of the text where the placed text appears
	  * @param statementId Id of the statement made within the linked text / entity
	  * @param orderIndex 0-based index that indicates the specific location of the placed text
	  * @author Mikko Hilpinen
	  * @since 01.09.2024
	  */
	private case class _StatementLinkData(parentId: Int, statementId: Int, orderIndex: Int = 0) 
		extends StatementLinkData
	{
		// IMPLEMENTED	--------------------
		
		/**
		  * @param parentId Id of the text where the placed text appears
		  * @param statementId Id of the statement made within the linked text / entity
		  * @param orderIndex 0-based index that indicates the specific location of the placed text
		  */
		override def copyStatementLink(parentId: Int, statementId: Int, orderIndex: Int = 0) = 
			_StatementLinkData(parentId, statementId, orderIndex)
	}
}

/**
  * Common trait for links between statements and the texts where they are made
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkData 
	extends StatementLinkDataLike[StatementLinkData] with TextPlacementData 
		with TextPlacementDataLike[StatementLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def placedId = statementId
}

