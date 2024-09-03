package vf.llama.database.access.single.enums.value.version

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.enums.EnumValueVersionDbFactory
import vf.llama.model.stored.enums.EnumValueVersion

object UniqueEnumValueVersionAccess extends ViewFactory[UniqueEnumValueVersionAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueEnumValueVersionAccess = 
		_UniqueEnumValueVersionAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueEnumValueVersionAccess(override val accessCondition: Option[Condition]) 
		extends UniqueEnumValueVersionAccess
}

/**
  * A common trait for access points that return individual and distinct enum value versions.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueEnumValueVersionAccess 
	extends UniqueEnumValueVersionAccessLike[EnumValueVersion, UniqueEnumValueVersionAccess] 
		with SingleChronoRowModelAccess[EnumValueVersion, UniqueEnumValueVersionAccess] 
		with NullDeprecatableView[UniqueEnumValueVersionAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = EnumValueVersionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueEnumValueVersionAccess = 
		UniqueEnumValueVersionAccess(condition)
}

