package vf.llama.database.access.single.enums.value.version

import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.enums.StatementLinkedEnumValueVersionDbFactory
import vf.llama.database.storable.enums.EnumValueStatementLinkDbModel
import vf.llama.model.combined.enums.StatementLinkedEnumValueVersion

object UniqueStatementLinkedEnumValueVersionAccess 
	extends ViewFactory[UniqueStatementLinkedEnumValueVersionAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueStatementLinkedEnumValueVersionAccess = 
		_UniqueStatementLinkedEnumValueVersionAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueStatementLinkedEnumValueVersionAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniqueStatementLinkedEnumValueVersionAccess
}

/**
  * A common trait for access points that return distinct statement linked enum value versions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueStatementLinkedEnumValueVersionAccess 
	extends UniqueEnumValueVersionAccessLike[StatementLinkedEnumValueVersion, UniqueStatementLinkedEnumValueVersionAccess] 
		with NullDeprecatableView[UniqueStatementLinkedEnumValueVersionAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = EnumValueStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedEnumValueVersionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueStatementLinkedEnumValueVersionAccess = 
		UniqueStatementLinkedEnumValueVersionAccess(condition)
}

