package vf.llama.database.access.many.conversation.message

import utopia.echo.model.enumeration.ChatRole
import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.conversation.MessageDbModel

/**
  * A common trait for access points which target multiple messages or similar instances at a time
  * @tparam A Type of read (messages -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyMessagesAccessLike[+A, +Repr] extends ManyModelAccess[A] with Indexed with FilterableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * session ids of the accessible messages
	  */
	def sessionIds(implicit connection: Connection) = pullColumn(model.sessionId.column).map { v => v.getInt }
	
	/**
	  * senders of the accessible messages
	  */
	def senders(implicit connection: Connection) = 
		pullColumn(model.sender.column).map { v => v.getInt }.flatMap(ChatRole.findForId)
	
	/**
	  * creation times of the accessible messages
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * Unique ids of the accessible messages
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = MessageDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * @param sender sender to target
	  * @return Copy of this access point that only includes messages with the specified sender
	  */
	def by(sender: ChatRole) = filter(model.sender.column <=> sender.id)
	
	/**
	  * @param senders Targeted senders
	  * 
		@return Copy of this access point that only includes messages where sender is within the specified value set
	  */
	def bySenders(senders: Iterable[ChatRole]) =
		filter(model.sender.column.in(senders.map { sender => sender.id }))
	
	/**
	  * @param sessionId session id to target
	  * @return Copy of this access point that only includes messages with the specified session id
	  */
	def postedDuringSession(sessionId: Int) = filter(model.sessionId.column <=> sessionId)
	
	/**
	  * @param sessionIds Targeted session ids
	  * @return Copy of this access point that only includes messages where session id is within the specified
	  *  value set
	  */
	def postedDuringSessions(sessionIds: IterableOnce[Int]) = 
		filter(model.sessionId.column.in(IntSet.from(sessionIds)))
}

