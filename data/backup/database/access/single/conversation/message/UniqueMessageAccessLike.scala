package vf.llama.database.access.single.conversation.message

import utopia.echo.model.enumeration.ChatRole
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.conversation.MessageDbModel

/**
  * A common trait for access points which target individual messages or similar items at a time
  * @tparam A Type of read (messages -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueMessageAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the session during which this message was posted. 
	  * None if no message (or value) was found.
	  */
	def sessionId(implicit connection: Connection) = pullColumn(model.sessionId.column).int
	
	/**
	  * Role of the entity that sent this message. 
	  * None if no message (or value) was found.
	  */
	def sender(implicit connection: Connection) = 
		pullColumn(model.sender.column).int.flatMap(ChatRole.findForId)
	
	/**
	  * Time when this message was posted. 
	  * None if no message (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Unique id of the accessible message. None if no message was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = MessageDbModel
}

