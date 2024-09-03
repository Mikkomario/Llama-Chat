package vf.llama.database.access.single.enums.value.version

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.enums.ContextualEnumValueVersionDbFactory
import vf.llama.database.storable.enums.EnumValueDbModel
import vf.llama.model.combined.enums.ContextualEnumValueVersion

import java.time.Instant

object UniqueContextualEnumValueVersionAccess extends ViewFactory[UniqueContextualEnumValueVersionAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueContextualEnumValueVersionAccess = 
		_UniqueContextualEnumValueVersionAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueContextualEnumValueVersionAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniqueContextualEnumValueVersionAccess
}

/**
  * A common trait for access points that return distinct contextual enum value versions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueContextualEnumValueVersionAccess 
	extends UniqueEnumValueVersionAccessLike[ContextualEnumValueVersion, UniqueContextualEnumValueVersionAccess] 
		with SingleChronoRowModelAccess[ContextualEnumValueVersion, UniqueContextualEnumValueVersionAccess] 
		with NullDeprecatableView[UniqueContextualEnumValueVersionAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the enumeration where this value appears. 
	  * None if no enum value (or value) was found.
	  */
	def valueEnumId(implicit connection: Connection) = pullColumn(valueModel.enumId.column).int
	
	/**
	  * Time when this enum value was added to the database. 
	  * None if no enum value (or value) was found.
	  */
	def valueCreated(implicit connection: Connection) = pullColumn(valueModel.created.column).instant
	
	/**
	  * Time when this enumeration value was archived. 
	  * None if no enum value (or value) was found.
	  */
	def valueArchivedAfter(implicit connection: Connection) = pullColumn(valueModel
		.archivedAfter.column).instant
	
	/**
	  * A database model (factory) used for interacting with the linked value
	  */
	protected def valueModel = EnumValueDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ContextualEnumValueVersionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueContextualEnumValueVersionAccess = 
		UniqueContextualEnumValueVersionAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the archive times of the targeted enum values
	  * @param newArchivedAfter A new archived after to assign
	  * @return Whether any enum value was affected
	  */
	def valueArchivedAfter_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(valueModel.archivedAfter.column, newArchivedAfter)
}

