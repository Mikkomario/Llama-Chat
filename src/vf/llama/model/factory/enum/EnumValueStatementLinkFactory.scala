package vf.llama.model.factory.`enum`

import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for enum value statement link-related factories which allow construction 
  * with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait EnumValueStatementLinkFactory[+A] extends StatementLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param enumValueVersionId New enum value version id to assign
	  * @return Copy of this item with the specified enum value version id
	  */
	def withEnumValueVersionId(enumValueVersionId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withParentId(parentId: Int) = withEnumValueVersionId(parentId)
}

