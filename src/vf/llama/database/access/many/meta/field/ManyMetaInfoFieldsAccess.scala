package vf.llama.database.access.many.meta.field

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.MetaInfoFieldDbFactory
import vf.llama.model.stored.meta.MetaInfoField

object ManyMetaInfoFieldsAccess extends ViewFactory[ManyMetaInfoFieldsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyMetaInfoFieldsAccess = 
		_ManyMetaInfoFieldsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyMetaInfoFieldsAccess(override val accessCondition: Option[Condition]) 
		extends ManyMetaInfoFieldsAccess
}

/**
  * A common trait for access points which target multiple meta info fields at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyMetaInfoFieldsAccess 
	extends ManyMetaInfoFieldsAccessLike[MetaInfoField, ManyMetaInfoFieldsAccess] 
		with ManyRowModelAccess[MetaInfoField]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MetaInfoFieldDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyMetaInfoFieldsAccess = ManyMetaInfoFieldsAccess(condition)
}

