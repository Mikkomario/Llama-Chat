package vf.llama.database.access.many.meta.custom

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{ChronoRowFactoryView, NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.CustomMetaInfoDbFactory
import vf.llama.database.storable.meta.CustomMetaInfoDbModel
import vf.llama.model.stored.meta.CustomMetaInfo

import java.time.Instant

object ManyCustomMetaInfosAccess extends ViewFactory[ManyCustomMetaInfosAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyCustomMetaInfosAccess = 
		_ManyCustomMetaInfosAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyCustomMetaInfosAccess(override val accessCondition: Option[Condition]) 
		extends ManyCustomMetaInfosAccess
}

/**
  * A common trait for access points which target multiple custom meta infos at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyCustomMetaInfosAccess 
	extends ManyRowModelAccess[CustomMetaInfo] 
		with ChronoRowFactoryView[CustomMetaInfo, ManyCustomMetaInfosAccess] 
		with NullDeprecatableView[ManyCustomMetaInfosAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * names of the accessible custom meta infos
	  */
	def names(implicit connection: Connection) = pullColumn(model.name.column).flatMap { _.string }
	
	/**
	  * values of the accessible custom meta infos
	  */
	def values(implicit connection: Connection) = pullColumn(model.value.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible custom meta infos
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible custom meta infos
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible custom meta infos
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = CustomMetaInfoDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = CustomMetaInfoDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyCustomMetaInfosAccess = ManyCustomMetaInfosAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted custom meta infos
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any custom meta info was affected
	  */
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
	
	/**
	  * @param name name to target
	  * @return Copy of this access point that only includes custom meta infos with the specified name
	  */
	def withName(name: String) = filter(model.name.column <=> name)
	
	/**
	  * @param names Targeted names
	  * @return Copy of this access point that only includes custom meta infos where name is within the
	  *  specified value set
	  */
	def withNames(names: Iterable[String]) = filter(model.name.column.in(names))
}

