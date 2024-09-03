package vf.llama.model.partial.enums

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import vf.llama.model.factory.enums.EnumValueStatementLinkFactory
import vf.llama.model.partial.statement.{StatementLinkData, StatementLinkDataLike}

object EnumValueStatementLinkData extends FromModelFactoryWithSchema[EnumValueStatementLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("enumValueVersionId", IntType, 
			Vector("enum_value_version_id", "parentId", "parent_id")), PropertyDeclaration("statementId", 
			IntType, Single("statement_id")), PropertyDeclaration("orderIndex", IntType, 
			Single("order_index"), 0)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		EnumValueStatementLinkData(valid("enumValueVersionId").getInt, valid("statementId").getInt, 
			valid("orderIndex").getInt)
}

/**
  * Common trait for links between statements and the texts where they are made
  * @param enumValueVersionId Id of the enumeration value version where the linked statement appears
  * @param statementId Id of the statement made within the linked text / entity
  * @param orderIndex 0-based index that indicates the specific location of the placed text
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumValueStatementLinkData(enumValueVersionId: Int, statementId: Int, orderIndex: Int = 0) 
	extends EnumValueStatementLinkFactory[EnumValueStatementLinkData] with StatementLinkData 
		with StatementLinkDataLike[EnumValueStatementLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def parentId = enumValueVersionId
	
	override def copyStatementLink(statementId: Int, parentId: Int, orderIndex: Int) = 
		copy(enumValueVersionId = parentId, statementId = statementId, orderIndex = orderIndex)
	
	override
		 def withEnumValueVersionId(enumValueVersionId: Int) = copy(enumValueVersionId = enumValueVersionId)
}

