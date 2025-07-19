package vf.llama.database.access.single.conversation.session

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.conversation.SessionDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual sessions or similar items at a time
  * @tparam A Type of read (sessions -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueSessionAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the conversation active during this session. 
	  * None if no session (or value) was found.
	  */
	def conversationId(implicit connection: Connection) = pullColumn(model.conversationId.column).int
	
	/**
	  * Time when this session started. 
	  * None if no session (or value) was found.
	  */
	def started(implicit connection: Connection) = pullColumn(model.started.column).instant
	
	/**
	  * Time when this session ended. None while active. 
	  * None if no session (or value) was found.
	  */
	def ended(implicit connection: Connection) = pullColumn(model.ended.column).instant
	
	/**
	  * Unique id of the accessible session. None if no session was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
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
	def ended_=(newEnded: Instant)(implicit connection: Connection) = putColumn(model.ended.column, newEnded)
}

