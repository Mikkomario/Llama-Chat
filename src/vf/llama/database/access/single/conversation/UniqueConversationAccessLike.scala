package vf.llama.database.access.single.conversation

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.conversation.ConversationDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual conversations or similar items at a time
  * @tparam A Type of read (conversations -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueConversationAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the thread this conversation is part of. 
	  * None if no conversation (or value) was found.
	  */
	def threadId(implicit connection: Connection) = pullColumn(model.threadId.column).int
	
	/**
	  * Time when this conversation was added to the database. 
	  * None if no conversation (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this conversation was closed the last time. 
	  * None if no conversation (or value) was found.
	  */
	def closedAfter(implicit connection: Connection) = pullColumn(model.closedAfter.column).instant
	
	/**
	  * Unique id of the accessible conversation. None if no conversation was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
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
	def closedAfter_=(newClosedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.closedAfter.column, newClosedAfter)
}

