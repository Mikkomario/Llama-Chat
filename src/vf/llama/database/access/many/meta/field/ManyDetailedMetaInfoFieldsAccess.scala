package vf.llama.database.access.many.meta.field

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.DetailedMetaInfoFieldDbFactory
import vf.llama.database.storable.meta.CustomMetaInfoDbModel
import vf.llama.model.combined.meta.DetailedMetaInfoField

import java.time.Instant

object ManyDetailedMetaInfoFieldsAccess extends ViewFactory[ManyDetailedMetaInfoFieldsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyDetailedMetaInfoFieldsAccess = 
		_ManyDetailedMetaInfoFieldsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyDetailedMetaInfoFieldsAccess(override val accessCondition: Option[Condition]) 
		extends ManyDetailedMetaInfoFieldsAccess
}

/**
  * A common trait for access points that return multiple detailed meta info fields at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyDetailedMetaInfoFieldsAccess 
	extends ManyMetaInfoFieldsAccessLike[DetailedMetaInfoField, ManyDetailedMetaInfoFieldsAccess] 
		with ManyRowModelAccess[DetailedMetaInfoField]
{
	// COMPUTED	--------------------
	
	/**
	  * names of the accessible custom meta infos
	  */
	def customInfoNames(implicit connection: Connection) = 
		pullColumn(customInfoModel.name.column).flatMap { _.string }
	
	/**
	  * values of the accessible custom meta infos
	  */
	def customInfoValues(implicit connection: Connection) = 
		pullColumn(customInfoModel.value.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible custom meta infos
	  */
	def customInfoCreationTimes(implicit connection: Connection) = 
		pullColumn(customInfoModel.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible custom meta infos
	  */
	def customInfoDeprecationTimes(implicit connection: Connection) = 
		pullColumn(customInfoModel.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Model (factory) used for interacting the custom meta infos associated 
		with this detailed meta info field
	  */
	protected def customInfoModel = CustomMetaInfoDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = DetailedMetaInfoFieldDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyDetailedMetaInfoFieldsAccess = 
		ManyDetailedMetaInfoFieldsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted custom meta infos
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any custom meta info was affected
	  */
	def customInfoDeprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(customInfoModel.deprecatedAfter.column, newDeprecatedAfter)
}

