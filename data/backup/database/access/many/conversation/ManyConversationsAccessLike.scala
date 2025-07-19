package vf.llama.database.access.many.conversation

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.conversation.ConversationDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple conversations or similar instances at a time
  * @tparam A Type of read (conversations -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyConversationsAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * thread ids of the accessible conversations
	  */
	def threadIds(implicit connection: Connection) = pullColumn(model.threadId.column).map { v => v.getInt }
	
	/**
	  * creation times of the accessible conversations
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * closing times of the accessible conversations
	  */
	def closingTimes(implicit connection: Connection) = 
		pullColumn(model.closedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible conversations
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = ConversationDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the closing times of the targeted conversations
	  * @param newClosedAfter A new closed after to assign
	  * @return Whether any conversation was affected
	  */
	def closingTimes_=(newClosedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.closedAfter.column, newClosedAfter)
	
	/**
	  * @param threadId thread id to target
	  * @return Copy of this access point that only includes conversations with the specified thread id
	  */
	def withinThread(threadId: Int) = filter(model.threadId.column <=> threadId)
	
	/**
	  * @param threadIds Targeted thread ids
	  * 
		@return Copy of this access point that only includes conversations where thread id is within the specified
	  *  value set
	  */
	def withinThreads(threadIds: IterableOnce[Int]) = filter(model.threadId.column.in(IntSet.from(threadIds)))
}

