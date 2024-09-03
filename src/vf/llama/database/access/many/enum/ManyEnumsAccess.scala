package vf.llama.database.access.many.`enum`

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.enum.EnumDbFactory
import vf.llama.database.storable.enum.EnumDbModel
import vf.llama.model.stored.enum.Enum

import java.time.Instant

object ManyEnumsAccess extends ViewFactory[ManyEnumsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyEnumsAccess = _ManyEnumsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyEnumsAccess(override val accessCondition: Option[Condition])
		 extends ManyEnumsAccess
}

/**
  * A common trait for access points which target multiple enums at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyEnumsAccess extends ManyRowModelAccess[Enum] with NullDeprecatableView[ManyEnumsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * names of the accessible enums
	  */
	def names(implicit connection: Connection) = pullColumn(model.name.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible enums
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * archive times of the accessible enums
	  */
	def archiveTimes(implicit connection: Connection) = 
		pullColumn(model.archivedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible enums
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = EnumDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyEnumsAccess = ManyEnumsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the archive times of the targeted enums
	  * @param newArchivedAfter A new archived after to assign
	  * @return Whether any enum was affected
	  */
	def archiveTimes_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
	
	/**
	  * Updates the names of the targeted enums
	  * @param newName A new name to assign
	  * @return Whether any enum was affected
	  */
	def names_=(newName: String)(implicit connection: Connection) = putColumn(model.name.column, newName)
	
	/**
	  * @param name name to target
	  * @return Copy of this access point that only includes enums with the specified name
	  */
	def withName(name: String) = filter(model.name.column <=> name)
	
	/**
	  * @param names Targeted names
	  * 
		@return Copy of this access point that only includes enums where name is within the specified value set
	  */
	def withNames(names: Iterable[String]) = filter(model.name.column.in(names))
}

