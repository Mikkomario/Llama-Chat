package vf.llama.database.access.many.instruction.version

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{ChronoRowFactoryView, NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.InstructionVersionDbFactory
import vf.llama.database.storable.instruction.InstructionVersionDbModel
import vf.llama.model.stored.instruction.InstructionVersion

import java.time.Instant

object ManyInstructionVersionsAccess extends ViewFactory[ManyInstructionVersionsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyInstructionVersionsAccess = 
		_ManyInstructionVersionsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyInstructionVersionsAccess(override val accessCondition: Option[Condition]) 
		extends ManyInstructionVersionsAccess
}

/**
  * A common trait for access points which target multiple instruction versions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyInstructionVersionsAccess 
	extends ManyRowModelAccess[InstructionVersion] 
		with ChronoRowFactoryView[InstructionVersion, ManyInstructionVersionsAccess] 
		with NullDeprecatableView[ManyInstructionVersionsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * names of the accessible instruction versions
	  */
	def names(implicit connection: Connection) = pullColumn(model.name.column).flatMap { _.string }
	
	/**
	  * enum value ids of the accessible instruction versions
	  */
	def enumValueIds(implicit connection: Connection) = 
		pullColumn(model.enumValueId.column).flatMap { v => v.int }
	
	/**
	  * creation times of the accessible instruction versions
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible instruction versions
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible instruction versions
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = InstructionVersionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = InstructionVersionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyInstructionVersionsAccess = 
		ManyInstructionVersionsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted instruction versions
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any instruction version was affected
	  */
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
	
	/**
	  * Updates the enum value ids of the targeted instruction versions
	  * @param newEnumValueId A new enum value id to assign
	  * @return Whether any instruction version was affected
	  */
	def enumValueIds_=(newEnumValueId: Int)(implicit connection: Connection) = 
		putColumn(model.enumValueId.column, newEnumValueId)
	
	/**
	  * @param enumValueId enum value id to target
	  * @return Copy of this access point that only includes instruction versions 
		with the specified enum value id
	  */
	def referringToEnumValue(enumValueId: Int) = filter(model.enumValueId.column <=> enumValueId)
	
	/**
	  * @param enumValueIds Targeted enum value ids
	  * 
		@return Copy of this access point that only includes instruction versions where enum value id is within
	  *  the specified value set
	  */
	def referringToEnumValues(enumValueIds: IterableOnce[Int]) = 
		filter(model.enumValueId.column.in(IntSet.from(enumValueIds)))
	
	/**
	  * @param name name to target
	  * @return Copy of this access point that only includes instruction versions with the specified name
	  */
	def withName(name: String) = filter(model.name.column <=> name)
	
	/**
	  * @param names Targeted names
	  * 
		@return Copy of this access point that only includes instruction versions where name is within the specified
	  *  value set
	  */
	def withNames(names: Iterable[String]) = filter(model.name.column.in(names))
}

