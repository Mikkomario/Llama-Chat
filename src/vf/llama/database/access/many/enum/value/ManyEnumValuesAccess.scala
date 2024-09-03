package vf.llama.database.access.many.`enum`.value

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.enum.EnumValueDbFactory
import vf.llama.database.storable.enum.EnumValueDbModel
import vf.llama.model.stored.enum.EnumValue

import java.time.Instant

object ManyEnumValuesAccess extends ViewFactory[ManyEnumValuesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyEnumValuesAccess = _ManyEnumValuesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyEnumValuesAccess(override val accessCondition: Option[Condition]) 
		extends ManyEnumValuesAccess
}

/**
  * A common trait for access points which target multiple enum values at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyEnumValuesAccess 
	extends ManyRowModelAccess[EnumValue] with NullDeprecatableView[ManyEnumValuesAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * enum ids of the accessible enum values
	  */
	def enumIds(implicit connection: Connection) = pullColumn(model.enumId.column).map { v => v.getInt }
	
	/**
	  * creation times of the accessible enum values
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * archive times of the accessible enum values
	  */
	def archiveTimes(implicit connection: Connection) = 
		pullColumn(model.archivedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible enum values
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = EnumValueDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumValueDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyEnumValuesAccess = ManyEnumValuesAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the archive times of the targeted enum values
	  * @param newArchivedAfter A new archived after to assign
	  * @return Whether any enum value was affected
	  */
	def archiveTimes_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
	
	/**
	  * @param enumId enum id to target
	  * @return Copy of this access point that only includes enum values with the specified enum id
	  */
	def inEnum(enumId: Int) = filter(model.enumId.column <=> enumId)
	
	/**
	  * @param enumIds Targeted enum ids
	  * @return Copy of this access point that only includes enum values where enum id is within the
	  *  specified value set
	  */
	def inEnums(enumIds: IterableOnce[Int]) = filter(model.enumId.column.in(IntSet.from(enumIds)))
}

