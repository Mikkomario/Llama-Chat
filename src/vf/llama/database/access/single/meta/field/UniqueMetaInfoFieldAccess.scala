package vf.llama.database.access.single.meta.field

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.MetaInfoFieldDbFactory
import vf.llama.model.stored.meta.MetaInfoField

object UniqueMetaInfoFieldAccess extends ViewFactory[UniqueMetaInfoFieldAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueMetaInfoFieldAccess = 
		_UniqueMetaInfoFieldAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueMetaInfoFieldAccess(override val accessCondition: Option[Condition]) 
		extends UniqueMetaInfoFieldAccess
}

/**
  * A common trait for access points that return individual and distinct meta info fields.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueMetaInfoFieldAccess 
	extends UniqueMetaInfoFieldAccessLike[MetaInfoField, UniqueMetaInfoFieldAccess] 
		with SingleRowModelAccess[MetaInfoField] with NullDeprecatableView[UniqueMetaInfoFieldAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MetaInfoFieldDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueMetaInfoFieldAccess = UniqueMetaInfoFieldAccess(condition)
}

