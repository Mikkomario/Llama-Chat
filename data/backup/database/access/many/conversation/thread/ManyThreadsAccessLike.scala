package vf.llama.database.access.many.conversation.thread

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.conversation.ThreadDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple threads or similar instances at a time
  * @tparam A Type of read (threads -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyThreadsAccessLike[+A, +Repr] extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * topic ids of the accessible threads
	  */
	def topicIds(implicit connection: Connection) = pullColumn(model.topicId.column).map { v => v.getInt }
	
	/**
	  * names of the accessible threads
	  */
	def names(implicit connection: Connection) = pullColumn(model.name.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible threads
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * archive times of the accessible threads
	  */
	def archiveTimes(implicit connection: Connection) = 
		pullColumn(model.archivedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible threads
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = ThreadDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the archive times of the targeted threads
	  * @param newArchivedAfter A new archived after to assign
	  * @return Whether any thread was affected
	  */
	def archiveTimes_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
	
	/**
	  * Updates the names of the targeted threads
	  * @param newName A new name to assign
	  * @return Whether any thread was affected
	  */
	def names_=(newName: String)(implicit connection: Connection) = putColumn(model.name.column, newName)
	
	/**
	  * @param name name to target
	  * @return Copy of this access point that only includes threads with the specified name
	  */
	def withName(name: String) = filter(model.name.column <=> name)
	
	/**
	  * @param names Targeted names
	  * 
		@return Copy of this access point that only includes threads where name is within the specified value set
	  */
	def withNames(names: Iterable[String]) = filter(model.name.column.in(names))
	
	/**
	  * @param topicId topic id to target
	  * @return Copy of this access point that only includes threads with the specified topic id
	  */
	def withinTopic(topicId: Int) = filter(model.topicId.column <=> topicId)
	
	/**
	  * @param topicIds Targeted topic ids
	  * 
		@return Copy of this access point that only includes threads where topic id is within the specified value set
	  */
	def withinTopics(topicIds: IterableOnce[Int]) = filter(model.topicId.column.in(IntSet.from(topicIds)))
}

