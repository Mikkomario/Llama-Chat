package vf.llama.database.access.many.conversation.session

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.conversation.SessionDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple sessions or similar instances at a time
  * @tparam A Type of read (sessions -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManySessionsAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * conversation ids of the accessible sessions
	  */
	def conversationIds(implicit connection: Connection) = 
		pullColumn(model.conversationId.column).map { v => v.getInt }
	
	/**
	  * start times of the accessible sessions
	  */
	def startTimes(implicit connection: Connection) = pullColumn(model.started.column)
		.map { v => v.getInstant }
	
	/**
	  * end times of the accessible sessions
	  */
	def endTimes(implicit connection: Connection) = pullColumn(model.ended.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible sessions
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = SessionDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the end times of the targeted sessions
	  * @param newEnded A new ended to assign
	  * @return Whether any session was affected
	  */
	def endTimes_=(newEnded: Instant)(implicit connection: Connection) = putColumn(model.ended.column, 
		newEnded)
	
	/**
	  * @param conversationId conversation id to target
	  * @return Copy of this access point that only includes sessions with the specified conversation id
	  */
	def ofConversation(conversationId: Int) = filter(model.conversationId.column <=> conversationId)
	
	/**
	  * @param conversationIds Targeted conversation ids
	  * 
		@return Copy of this access point that only includes sessions where conversation id is within the specified
	  *  value set
	  */
	def ofConversations(conversationIds: IterableOnce[Int]) = 
		filter(model.conversationId.column.in(IntSet.from(conversationIds)))
}

