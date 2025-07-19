package vf.llama.database.access.single.meta.field

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.meta.MetaInfoFieldDbModel
import vf.llama.model.enumeration.AutomatedMetaInfo

import java.time.Instant

/**
  * A common trait for access points which target individual meta info fields or similar items at a time
  * @tparam A Type of read (meta info fields -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueMetaInfoFieldAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Automatically fillable value. None if this is not an automated field. 
	  * None if no meta info field (or value) was found.
	  */
	def automatedContent(implicit connection: Connection) = 
		pullColumn(model.automatedContent.column).int.flatMap(AutomatedMetaInfo.findForId)
	
	/**
	  * Id of the customized information in this field. None if this is an automated field. 
	  * None if no meta info field (or value) was found.
	  */
	def customInfoId(implicit connection: Connection) = pullColumn(model.customInfoId.column).int
	
	/**
	  * Time when this meta info field was added to the database. 
	  * None if no meta info field (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this field was archived, if applicable. 
	  * None if no meta info field (or value) was found.
	  */
	def archivedAfter(implicit connection: Connection) = pullColumn(model.archivedAfter.column).instant
	
	/**
	  * Unique id of the accessible meta info field. None if no meta info field was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
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
	def archivedAfter_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
	
	/**
	  * Updates the custom info ids of the targeted meta info fields
	  * @param newCustomInfoId A new custom info id to assign
	  * @return Whether any meta info field was affected
	  */
	def customInfoId_=(newCustomInfoId: Int)(implicit connection: Connection) = 
		putColumn(model.customInfoId.column, newCustomInfoId)
}

