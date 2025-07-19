package vf.llama.database.access.many.conversation.memory

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.conversation.MemoryDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple memories or similar instances at a time
  * @tparam A Type of read (memories -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyMemoriesAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * message ids of the accessible memories
	  */
	def messageIds(implicit connection: Connection) = pullColumn(model.messageId.column).map { v => v.getInt }
	
	/**
	  * archive times of the accessible memories
	  */
	def archiveTimes(implicit connection: Connection) = 
		pullColumn(model.archivedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible memories
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = MemoryDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the archive times of the targeted memories
	  * @param newArchivedAfter A new archived after to assign
	  * @return Whether any memory was affected
	  */
	def archiveTimes_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
	
	/**
	  * @param messageId message id to target
	  * @return Copy of this access point that only includes memories with the specified message id
	  */
	def fromMessage(messageId: Int) = filter(model.messageId.column <=> messageId)
	
	/**
	  * @param messageIds Targeted message ids
	  * @return Copy of this access point that only includes memories where message id is within the specified
	  *  value set
	  */
	def fromMessages(messageIds: IterableOnce[Int]) = filter(model
		.messageId.column.in(IntSet.from(messageIds)))
}

