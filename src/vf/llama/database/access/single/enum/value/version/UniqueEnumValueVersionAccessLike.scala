package vf.llama.database.access.single.`enum`.value.version

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.enum.EnumValueVersionDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual enum value versions or similar items at a time
  * @tparam A Type of read (enum value versions -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueEnumValueVersionAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the enumeration value which this version describes. 
	  * None if no enum value version (or value) was found.
	  */
	def enumValueId(implicit connection: Connection) = pullColumn(model.enumValueId.column).int
	
	/**
	  * Name of this enumeration value. 
	  * None if no enum value version (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.name.column).getString
	
	/**
	  * Id of the color used for this enumeration value. 
	  * None if no enum value version (or value) was found.
	  */
	def colorId(implicit connection: Connection) = pullColumn(model.colorId.column).int
	
	/**
	  * Time when this enum value version was added to the database. 
	  * None if no enum value version (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this version was replaced with a newer one. 
	  * None if no enum value version (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfter.column).instant
	
	/**
	  * Unique id of the accessible enum value version. None if no enum value version was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
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
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
}

