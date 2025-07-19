package vf.llama.database.access.single.instruction.link

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.props.instruction.InstructionTargetingLinkDbProps

import java.time.Instant

/**
  * A common trait for access points which target individual instruction targeting links or similar items
  *  at a time
  * @tparam A Type of read (instruction targeting links -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueInstructionTargetingLinkAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// ABSTRACT	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model: InstructionTargetingLinkDbProps
	
	
	// COMPUTED	--------------------
	
	/**
	  * Id of the entity to which or where the linked instruction applies. 
	  * None if no instruction targeting link (or value) was found.
	  */
	def targetId(implicit connection: Connection) = pullColumn(model.targetId.column).int
	
	/**
	  * Id of the instruction applied to the linked entity. 
	  * None if no instruction targeting link (or value) was found.
	  */
	def instructionId(implicit connection: Connection) = pullColumn(model.instructionId.column).int
	
	/**
	  * Time when this instruction targeting link was added to the database. 
	  * None if no instruction targeting link (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time after which this link was removed. 
	  * None if no instruction targeting link (or value) was found.
	  */
	def removedAfter(implicit connection: Connection) = pullColumn(model.removedAfter.column).instant
	
	/**
	  * 
		Unique id of the accessible instruction targeting link. None if no instruction targeting link was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the remove times of the targeted instruction targeting links
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any instruction targeting link was affected
	  */
	def removedAfter_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
}

