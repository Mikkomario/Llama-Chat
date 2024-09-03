package vf.llama.database.access.many.`enum`.value.version

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.enum.StatementLinkedEnumValueVersionDbFactory
import vf.llama.database.storable.enum.EnumValueStatementLinkDbModel
import vf.llama.model.combined.enum.StatementLinkedEnumValueVersion

object ManyStatementLinkedEnumValueVersionsAccess 
	extends ViewFactory[ManyStatementLinkedEnumValueVersionsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyStatementLinkedEnumValueVersionsAccess = 
		_ManyStatementLinkedEnumValueVersionsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyStatementLinkedEnumValueVersionsAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManyStatementLinkedEnumValueVersionsAccess
}

/**
  * A common trait for access points that return multiple statement linked enum value versions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyStatementLinkedEnumValueVersionsAccess 
	extends ManyEnumValueVersionsAccessLike[StatementLinkedEnumValueVersion, 
		ManyStatementLinkedEnumValueVersionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * enum value version ids of the accessible enum value statement links
	  */
	def linkEnumValueVersionIds(implicit connection: Connection) = 
		pullColumn(linkModel.enumValueVersionId.column).map { v => v.getInt }
	
	/**
	  * statement ids of the accessible enum value statement links
	  */
	def linkStatementIds(implicit connection: Connection) = 
		pullColumn(linkModel.statementId.column).map { v => v.getInt }
	
	/**
	  * order indices of the accessible enum value statement links
	  */
	def linkOrderIndices(implicit connection: Connection) = 
		pullColumn(linkModel.orderIndex.column).map { v => v.getInt }
	
	/**
	  * Model (factory) used for interacting the enum value statement links associated 
	  * with this statement linked enum value version
	  */
	protected def linkModel = EnumValueStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedEnumValueVersionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyStatementLinkedEnumValueVersionsAccess = 
		ManyStatementLinkedEnumValueVersionsAccess(condition)
}

