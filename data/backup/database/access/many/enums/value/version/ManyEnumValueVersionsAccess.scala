package vf.llama.database.access.many.enums.value.version

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.enums.EnumValueVersionDbFactory
import vf.llama.model.stored.enums.EnumValueVersion

object ManyEnumValueVersionsAccess extends ViewFactory[ManyEnumValueVersionsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyEnumValueVersionsAccess = 
		_ManyEnumValueVersionsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyEnumValueVersionsAccess(override val accessCondition: Option[Condition]) 
		extends ManyEnumValueVersionsAccess
}

/**
  * A common trait for access points which target multiple enum value versions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyEnumValueVersionsAccess 
	extends ManyEnumValueVersionsAccessLike[EnumValueVersion, ManyEnumValueVersionsAccess] 
		with ManyRowModelAccess[EnumValueVersion] 
		with ChronoRowFactoryView[EnumValueVersion, ManyEnumValueVersionsAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = EnumValueVersionDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): ManyEnumValueVersionsAccess = ManyEnumValueVersionsAccess(condition)
}

