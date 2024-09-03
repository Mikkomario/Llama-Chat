package vf.llama.database.access.many.`enum`.value.version

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.enum.EnumValueVersionDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple enum value versions or similar instances at a time
  * @tparam A Type of read (enum value versions -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyEnumValueVersionsAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * enum value ids of the accessible enum value versions
	  */
	def enumValueIds(implicit connection: Connection) = pullColumn(model.enumValueId.column)
		.map { v => v.getInt }
	
	/**
	  * names of the accessible enum value versions
	  */
	def names(implicit connection: Connection) = pullColumn(model.name.column).flatMap { _.string }
	
	/**
	  * color ids of the accessible enum value versions
	  */
	def colorIds(implicit connection: Connection) = pullColumn(model.colorId.column).map { v => v.getInt }
	
	/**
	  * creation times of the accessible enum value versions
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible enum value versions
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible enum value versions
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = EnumValueVersionDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted enum value versions
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any enum value version was affected
	  */
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
	
	/**
	  * @param enumValueId enum value id to target
	  * @return Copy of this access point that only includes enum value versions 
		with the specified enum value id
	  */
	def ofEnumValue(enumValueId: Int) = filter(model.enumValueId.column <=> enumValueId)
	
	/**
	  * @param enumValueIds Targeted enum value ids
	  * 
		@return Copy of this access point that only includes enum value versions where enum value id is within the
	  *  specified value set
	  */
	def ofEnumValues(enumValueIds: IterableOnce[Int]) = 
		filter(model.enumValueId.column.in(IntSet.from(enumValueIds)))
}

