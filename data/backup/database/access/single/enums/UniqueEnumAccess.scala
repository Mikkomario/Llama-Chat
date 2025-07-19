package vf.llama.database.access.single.enums

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.enums.EnumDbFactory
import vf.llama.database.storable.enums.EnumDbModel
import vf.llama.model.stored.enums.Enum

import java.time.Instant

object UniqueEnumAccess extends ViewFactory[UniqueEnumAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueEnumAccess = _UniqueEnumAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueEnumAccess(override val accessCondition: Option[Condition])
		 extends UniqueEnumAccess
}

/**
  * A common trait for access points that return individual and distinct enums.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueEnumAccess 
	extends SingleRowModelAccess[Enum] with DistinctModelAccess[Enum, Option[Enum], Value] 
		with NullDeprecatableView[UniqueEnumAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Name of this enumeration (mutable). 
	  * None if no enum (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.name.column).getString
	
	/**
	  * Time when this enum was added to the database. 
	  * None if no enum (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this enumeration was archived. 
	  * None if no enum (or value) was found.
	  */
	def archivedAfter(implicit connection: Connection) = pullColumn(model.archivedAfter.column).instant
	
	/**
	  * Unique id of the accessible enum. None if no enum was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = EnumDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueEnumAccess = UniqueEnumAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the archive times of the targeted enums
	  * @param newArchivedAfter A new archived after to assign
	  * @return Whether any enum was affected
	  */
	def archivedAfter_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
	
	/**
	  * Updates the names of the targeted enums
	  * @param newName A new name to assign
	  * @return Whether any enum was affected
	  */
	def name_=(newName: String)(implicit connection: Connection) = putColumn(model.name.column, newName)
}

