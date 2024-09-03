package vf.llama.database.access.many.`enum`.value.version.link.statement

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.many.statement.ManyStatementLinksAccessLike
import vf.llama.database.factory.enum.EnumValueStatementLinkDbFactory
import vf.llama.database.storable.enum.EnumValueStatementLinkDbModel
import vf.llama.model.stored.enum.EnumValueStatementLink

object ManyEnumValueStatementLinksAccess extends ViewFactory[ManyEnumValueStatementLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyEnumValueStatementLinksAccess = 
		_ManyEnumValueStatementLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyEnumValueStatementLinksAccess(override val accessCondition: Option[Condition]) 
		extends ManyEnumValueStatementLinksAccess
}

/**
  * A common trait for access points which target multiple enum value statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyEnumValueStatementLinksAccess 
	extends ManyStatementLinksAccessLike[EnumValueStatementLink, ManyEnumValueStatementLinksAccess] 
		with ManyRowModelAccess[EnumValueStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * enum value version ids of the accessible enum value statement links
	  */
	def enumValueVersionIds(implicit connection: Connection) = parentIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumValueStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = EnumValueStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyEnumValueStatementLinksAccess = 
		ManyEnumValueStatementLinksAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param enumValueVersionId enum value version id to target
	  * @return Copy of this access point that only includes enum value statement links 
	  * with the specified enum value version id
	  */
	def inEnumValueVersion(enumValueVersionId: Int) = 
		filter(model.enumValueVersionId.column <=> enumValueVersionId)
	
	/**
	  * @param enumValueVersionIds Targeted enum value version ids
	  * 
		@return Copy of this access point that only includes enum value statement links where enum value version id is
	  *  within the specified value set
	  */
	def inEnumValueVersions(enumValueVersionIds: IterableOnce[Int]) = 
		filter(model.enumValueVersionId.column.in(IntSet.from(enumValueVersionIds)))
}

