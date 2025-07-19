package vf.llama.database.access.single.conversation.memory

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.conversation.MemoryDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual memories or similar items at a time
  * @tparam A Type of read (memories -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueMemoryAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the message from which this memory was triggered. 
	  * None if no memory (or value) was found.
	  */
	def messageId(implicit connection: Connection) = pullColumn(model.messageId.column).int
	
	/**
	  * Time when this memory was archived. 
	  * None if no memory (or value) was found.
	  */
	def archivedAfter(implicit connection: Connection) = pullColumn(model.archivedAfter.column).instant
	
	/**
	  * Unique id of the accessible memory. None if no memory was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
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
	def archivedAfter_=(newArchivedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.archivedAfter.column, newArchivedAfter)
}

