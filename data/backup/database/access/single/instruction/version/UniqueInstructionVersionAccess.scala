package vf.llama.database.access.single.instruction.version

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.InstructionVersionDbFactory
import vf.llama.database.storable.instruction.InstructionVersionDbModel
import vf.llama.model.stored.instruction.InstructionVersion

import java.time.Instant

object UniqueInstructionVersionAccess extends ViewFactory[UniqueInstructionVersionAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueInstructionVersionAccess = 
		_UniqueInstructionVersionAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueInstructionVersionAccess(override val accessCondition: Option[Condition]) 
		extends UniqueInstructionVersionAccess
}

/**
  * A common trait for access points that return individual and distinct instruction versions.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueInstructionVersionAccess 
	extends SingleChronoRowModelAccess[InstructionVersion, UniqueInstructionVersionAccess] 
		with DistinctModelAccess[InstructionVersion, Option[InstructionVersion], Value] 
		with NullDeprecatableView[UniqueInstructionVersionAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Name of this instruction. 
	  * None if no instruction version (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.name.column).getString
	
	/**
	  * Id of the enumeration value selected by applying this instruction. None if not enumeration-based. 
	  * None if no instruction version (or value) was found.
	  */
	def enumValueId(implicit connection: Connection) = pullColumn(model.enumValueId.column).int
	
	/**
	  * Time when this instruction version was added to the database. 
	  * None if no instruction version (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this version was replaced with a new one. 
	  * None if no instruction version (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfter.column).instant
	
	/**
	  * Unique id of the accessible instruction version. None if no instruction version was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = InstructionVersionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = InstructionVersionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueInstructionVersionAccess = 
		UniqueInstructionVersionAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted instruction versions
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any instruction version was affected
	  */
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
	
	/**
	  * Updates the enum value ids of the targeted instruction versions
	  * @param newEnumValueId A new enum value id to assign
	  * @return Whether any instruction version was affected
	  */
	def enumValueId_=(newEnumValueId: Int)(implicit connection: Connection) = 
		putColumn(model.enumValueId.column, newEnumValueId)
}

