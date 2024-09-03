package vf.llama.database.access.single.meta.field

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.DetailedMetaInfoFieldDbFactory
import vf.llama.database.storable.meta.CustomMetaInfoDbModel
import vf.llama.model.combined.meta.DetailedMetaInfoField

import java.time.Instant

object UniqueDetailedMetaInfoFieldAccess extends ViewFactory[UniqueDetailedMetaInfoFieldAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueDetailedMetaInfoFieldAccess = 
		_UniqueDetailedMetaInfoFieldAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueDetailedMetaInfoFieldAccess(override val accessCondition: Option[Condition]) 
		extends UniqueDetailedMetaInfoFieldAccess
}

/**
  * A common trait for access points that return distinct detailed meta info fields
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueDetailedMetaInfoFieldAccess 
	extends UniqueMetaInfoFieldAccessLike[DetailedMetaInfoField, UniqueDetailedMetaInfoFieldAccess] 
		with SingleRowModelAccess[DetailedMetaInfoField] 
		with NullDeprecatableView[UniqueDetailedMetaInfoFieldAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Name of this field. 
	  * None if no custom meta info (or value) was found.
	  */
	def customInfoName(implicit connection: Connection) = pullColumn(customInfoModel.name.column).getString
	
	/**
	  * Value assigned to this field. 
	  * None if no custom meta info (or value) was found.
	  */
	def customInfoValue(implicit connection: Connection) = pullColumn(customInfoModel.value.column).getString
	
	/**
	  * Time when this custom meta info was added to the database. 
	  * None if no custom meta info (or value) was found.
	  */
	def customInfoCreated(implicit connection: Connection) = pullColumn(customInfoModel
		.created.column).instant
	
	/**
	  * Time when this version was replaced with another. 
	  * None if no custom meta info (or value) was found.
	  */
	def customInfoDeprecatedAfter(implicit connection: Connection) = 
		pullColumn(customInfoModel.deprecatedAfter.column).instant
	
	/**
	  * A database model (factory) used for interacting with the linked custom info
	  */
	protected def customInfoModel = CustomMetaInfoDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = DetailedMetaInfoFieldDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueDetailedMetaInfoFieldAccess = 
		UniqueDetailedMetaInfoFieldAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted custom meta infos
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any custom meta info was affected
	  */
	def customInfoDeprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(customInfoModel.deprecatedAfter.column, newDeprecatedAfter)
}

