package vf.llama.database.access.many.meta.field

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.meta.MetaInfoFieldDbModel
import vf.llama.model.enumeration.AutomatedMetaInfo

import java.time.Instant

/**
  * A common trait for access points which target multiple meta info fields or similar instances at a time
  * @tparam A Type of read (meta info fields -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyMetaInfoFieldsAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * automated contents of the accessible meta info fields
	  */
	def automatedContents(implicit connection: Connection) = 
		pullColumn(model.automatedContent.column).flatMap { v => v.int }.flatMap(AutomatedMetaInfo.findForId)
	
	/**
	  * custom info ids of the accessible meta info fields
	  */
	def customInfoIds(implicit connection: Connection) = 
		pullColumn(model.customInfoId.column).flatMap { v => v.int }
	
	/**
	  * creation times of the accessible meta info fields
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * archive times of the accessible meta info fields
	  */
	def archiveTimes(implicit connection: Connection) = 
		pullColumn(model.archivedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible meta info fields
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = MetaInfoFieldDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the archive times of the targeted meta info fields
	  * @param newArchivedAfter A new archived after to assign
	  * @return Whether any meta info field was affected
	  */
	def archiveTimes_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
	
	/**
	  * Updates the custom info ids of the targeted meta info fields
	  * @param newCustomInfoId A new custom info id to assign
	  * @return Whether any meta info field was affected
	  */
	def customInfoIds_=(newCustomInfoId: Int)(implicit connection: Connection) = 
		putColumn(model.customInfoId.column, newCustomInfoId)
	
	/**
	  * @param customInfoId custom info id to target
	  * @return Copy of this access point that only includes meta info fields 
		with the specified custom info id
	  */
	def withCustomInfo(customInfoId: Int) = filter(model.customInfoId.column <=> customInfoId)
	
	/**
	  * @param customInfoIds Targeted custom info ids
	  * 
		@return Copy of this access point that only includes meta info fields where custom info id is within the
	  *  specified value set
	  */
	def withCustomInfos(customInfoIds: IterableOnce[Int]) = 
		filter(model.customInfoId.column.in(IntSet.from(customInfoIds)))
}

