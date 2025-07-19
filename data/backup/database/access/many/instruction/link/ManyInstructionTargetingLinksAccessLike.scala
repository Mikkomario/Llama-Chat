package vf.llama.database.access.many.instruction.link

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.model.immutable.Storable
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.props.instruction.InstructionTargetingLinkDbProps

import java.time.Instant

/**
  * A common trait for access points which target multiple instruction targeting links or similar instances
  *  at a time
  * @tparam A Type of read (instruction targeting links -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyInstructionTargetingLinksAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model: InstructionTargetingLinkDbProps with NullDeprecatable[Storable]
	
	
	// COMPUTED	--------------------
	
	/**
	  * target ids of the accessible instruction targeting links
	  */
	def targetIds(implicit connection: Connection) = pullColumn(model.targetId.column).map { v => v.getInt }
	
	/**
	  * instruction ids of the accessible instruction targeting links
	  */
	def instructionIds(implicit connection: Connection) = 
		pullColumn(model.instructionId.column).map { v => v.getInt }
	
	/**
	  * creation times of the accessible instruction targeting links
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * remove times of the accessible instruction targeting links
	  */
	def removeTimes(implicit connection: Connection) = 
		pullColumn(model.removedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible instruction targeting links
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	
	// OTHER	--------------------
	
	/**
	  * @param targetId target id to target
	  * @return Copy of this access point that only includes instruction targeting links 
		with the specified target id
	  */
	def appliedIn(targetId: Int) = filter(model.targetId.column <=> targetId)
	
	/**
	  * @param targetIds Targeted target ids
	  * 
		@return Copy of this access point that only includes instruction targeting links where target id is within
	  *  the specified value set
	  */
	def appliedIn(targetIds: IterableOnce[Int]) = filter(model.targetId.column.in(IntSet.from(targetIds)))
	
	/**
	  * @param instructionId instruction id to target
	  * @return Copy of this access point that only includes instruction targeting links 
	  * with the specified instruction id
	  */
	def applyingInstruction(instructionId: Int) = filter(model.instructionId.column <=> instructionId)
	
	/**
	  * @param instructionIds Targeted instruction ids
	  * @return Copy of this access point that only includes instruction targeting links where instruction id
	  *  is within the specified value set
	  */
	def applyingInstructions(instructionIds: IterableOnce[Int]) = 
		filter(model.instructionId.column.in(IntSet.from(instructionIds)))
	
	/**
	  * Updates the remove times of the targeted instruction targeting links
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any instruction targeting link was affected
	  */
	def removeTimes_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
}

