package vf.llama.database.access.many.`enum`.value.version

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.enum.ContextualEnumValueVersionDbFactory
import vf.llama.database.storable.enum.EnumValueDbModel
import vf.llama.model.combined.enum.ContextualEnumValueVersion

import java.time.Instant

object ManyContextualEnumValueVersionsAccess extends ViewFactory[ManyContextualEnumValueVersionsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyContextualEnumValueVersionsAccess = 
		_ManyContextualEnumValueVersionsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyContextualEnumValueVersionsAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManyContextualEnumValueVersionsAccess
}

/**
  * A common trait for access points that return multiple contextual enum value versions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyContextualEnumValueVersionsAccess 
	extends ManyEnumValueVersionsAccessLike[ContextualEnumValueVersion, ManyContextualEnumValueVersionsAccess] 
		with ManyRowModelAccess[ContextualEnumValueVersion]
{
	// COMPUTED	--------------------
	
	/**
	  * enum ids of the accessible enum values
	  */
	def valueEnumIds(implicit connection: Connection) = pullColumn(valueModel.enumId.column)
		.map { v => v.getInt }
	
	/**
	  * creation times of the accessible enum values
	  */
	def valueCreationTimes(implicit connection: Connection) = 
		pullColumn(valueModel.created.column).map { v => v.getInstant }
	
	/**
	  * archive times of the accessible enum values
	  */
	def valueArchiveTimes(implicit connection: Connection) = 
		pullColumn(valueModel.archivedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Model (factory) used for interacting the enum values associated 
		with this contextual enum value version
	  */
	protected def valueModel = EnumValueDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ContextualEnumValueVersionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyContextualEnumValueVersionsAccess = 
		ManyContextualEnumValueVersionsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the archive times of the targeted enum values
	  * @param newArchivedAfter A new archived after to assign
	  * @return Whether any enum value was affected
	  */
	def valueArchiveTimes_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(valueModel.archivedAfter.column, newArchivedAfter)
}

