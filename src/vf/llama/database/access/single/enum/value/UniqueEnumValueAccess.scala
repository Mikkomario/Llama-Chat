package vf.llama.database.access.single.`enum`.value

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.enum.EnumValueDbFactory
import vf.llama.database.storable.enum.EnumValueDbModel
import vf.llama.model.stored.enum.EnumValue

import java.time.Instant

object UniqueEnumValueAccess extends ViewFactory[UniqueEnumValueAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueEnumValueAccess = _UniqueEnumValueAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueEnumValueAccess(override val accessCondition: Option[Condition]) 
		extends UniqueEnumValueAccess
}

/**
  * A common trait for access points that return individual and distinct enum values.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueEnumValueAccess 
	extends SingleRowModelAccess[EnumValue] with DistinctModelAccess[EnumValue, Option[EnumValue], Value] 
		with NullDeprecatableView[UniqueEnumValueAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the enumeration where this value appears. 
	  * None if no enum value (or value) was found.
	  */
	def enumId(implicit connection: Connection) = pullColumn(model.enumId.column).int
	
	/**
	  * Time when this enum value was added to the database. 
	  * None if no enum value (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this enumeration value was archived. 
	  * None if no enum value (or value) was found.
	  */
	def archivedAfter(implicit connection: Connection) = pullColumn(model.archivedAfter.column).instant
	
	/**
	  * Unique id of the accessible enum value. None if no enum value was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = EnumValueDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumValueDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueEnumValueAccess = UniqueEnumValueAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the archive times of the targeted enum values
	  * @param newArchivedAfter A new archived after to assign
	  * @return Whether any enum value was affected
	  */
	def archivedAfter_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
}

