package vf.llama.database.access.single.enums.value.version.link.statement

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.statement.UniqueStatementLinkAccessLike
import vf.llama.database.factory.enums.EnumValueStatementLinkDbFactory
import vf.llama.database.storable.enums.EnumValueStatementLinkDbModel
import vf.llama.model.stored.enums.EnumValueStatementLink

object UniqueEnumValueStatementLinkAccess extends ViewFactory[UniqueEnumValueStatementLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueEnumValueStatementLinkAccess = 
		_UniqueEnumValueStatementLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueEnumValueStatementLinkAccess(override val accessCondition: Option[Condition]) 
		extends UniqueEnumValueStatementLinkAccess
}

/**
  * A common trait for access points that return individual and distinct enum value statement links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueEnumValueStatementLinkAccess 
	extends UniqueStatementLinkAccessLike[EnumValueStatementLink, UniqueEnumValueStatementLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the enumeration value version where the linked statement appears. 
	  * None if no enum value statement link (or value) was found.
	  */
	def enumValueVersionId(implicit connection: Connection) = parentId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumValueStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = EnumValueStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueEnumValueStatementLinkAccess = 
		UniqueEnumValueStatementLinkAccess(condition)
}

