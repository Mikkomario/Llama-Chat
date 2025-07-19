package vf.llama.database.access.single.meta.custom

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.CustomMetaInfoDbFactory
import vf.llama.database.storable.meta.CustomMetaInfoDbModel
import vf.llama.model.stored.meta.CustomMetaInfo

import java.time.Instant

object UniqueCustomMetaInfoAccess extends ViewFactory[UniqueCustomMetaInfoAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueCustomMetaInfoAccess = 
		_UniqueCustomMetaInfoAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueCustomMetaInfoAccess(override val accessCondition: Option[Condition]) 
		extends UniqueCustomMetaInfoAccess
}

/**
  * A common trait for access points that return individual and distinct custom meta infos.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueCustomMetaInfoAccess 
	extends SingleChronoRowModelAccess[CustomMetaInfo, UniqueCustomMetaInfoAccess] 
		with DistinctModelAccess[CustomMetaInfo, Option[CustomMetaInfo], Value] 
		with NullDeprecatableView[UniqueCustomMetaInfoAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Name of this field. 
	  * None if no custom meta info (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.name.column).getString
	
	/**
	  * Value assigned to this field. 
	  * None if no custom meta info (or value) was found.
	  */
	def value(implicit connection: Connection) = pullColumn(model.value.column).getString
	
	/**
	  * Time when this custom meta info was added to the database. 
	  * None if no custom meta info (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this version was replaced with another. 
	  * None if no custom meta info (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfter.column).instant
	
	/**
	  * Unique id of the accessible custom meta info. None if no custom meta info was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = CustomMetaInfoDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = CustomMetaInfoDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): UniqueCustomMetaInfoAccess = UniqueCustomMetaInfoAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted custom meta infos
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any custom meta info was affected
	  */
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
}

