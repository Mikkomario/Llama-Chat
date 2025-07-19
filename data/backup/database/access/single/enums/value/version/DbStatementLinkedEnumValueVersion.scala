package vf.llama.database.access.single.enums.value.version

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.enums.StatementLinkedEnumValueVersionDbFactory
import vf.llama.database.storable.enums.{EnumValueStatementLinkDbModel, EnumValueVersionDbModel}
import vf.llama.model.combined.enums.StatementLinkedEnumValueVersion

/**
  * Used for accessing individual statement linked enum value versions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbStatementLinkedEnumValueVersion 
	extends SingleModelAccess[StatementLinkedEnumValueVersion] 
		with NonDeprecatedView[StatementLinkedEnumValueVersion] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = EnumValueStatementLinkDbModel
	
	/**
	  * A database model (factory) used for interacting with linked value versions
	  */
	private def model = EnumValueVersionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedEnumValueVersionDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted statement linked enum value version
	  * @return An access point to that statement linked enum value version
	  */
	def apply(id: Int) = DbSingleStatementLinkedEnumValueVersion(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique statement linked enum value versions.
	  * 
		@return An access point to the statement linked enum value version that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueStatementLinkedEnumValueVersionAccess(mergeCondition(additionalCondition))
}

