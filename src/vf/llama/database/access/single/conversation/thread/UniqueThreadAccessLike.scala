package vf.llama.database.access.single.conversation.thread

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.conversation.ThreadDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual threads or similar items at a time
  * @tparam A Type of read (threads -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueThreadAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the general topic of this thread. 
	  * None if no thread (or value) was found.
	  */
	def topicId(implicit connection: Connection) = pullColumn(model.topicId.column).int
	
	/**
	  * The current name of this thread. 
	  * None if no thread (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.name.column).getString
	
	/**
	  * Time when this thread was added to the database. 
	  * None if no thread (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this thread was archived. 
	  * None if no thread (or value) was found.
	  */
	def archivedAfter(implicit connection: Connection) = pullColumn(model.archivedAfter.column).instant
	
	/**
	  * Unique id of the accessible thread. None if no thread was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
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
	def archivedAfter_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
	
	/**
	  * Updates the names of the targeted threads
	  * @param newName A new name to assign
	  * @return Whether any thread was affected
	  */
	def name_=(newName: String)(implicit connection: Connection) = putColumn(model.name.column, newName)
}

