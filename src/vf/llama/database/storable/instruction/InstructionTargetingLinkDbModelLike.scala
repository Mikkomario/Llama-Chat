package vf.llama.database.storable.instruction

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.model.immutable.Storable
import utopia.vault.model.template.{FromIdFactory, HasId}
import vf.llama.database.props.instruction.InstructionTargetingLinkDbProps
import vf.llama.model.factory.instruction.InstructionTargetingLinkFactory

import java.time.Instant

/**
  * Common trait for database models used for interacting with instruction targeting link data in the database
  * @tparam Repr Type of this DB model
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkDbModelLike[+Repr] 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, Repr] 
		with InstructionTargetingLinkFactory[Repr]
{
	// ABSTRACT	--------------------
	
	def targetId: Option[Int]
	
	def instructionId: Option[Int]
	
	def created: Option[Instant]
	
	def removedAfter: Option[Instant]
	
	/**
	  * Access to the database properties which are utilized in this model
	  */
	def dbProps: InstructionTargetingLinkDbProps
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param targetId target id to assign to the new model (default = currently assigned value)
	  * @param instructionId instruction id to assign to the new model (default = currently assigned value)
	  * @param created created to assign to the new model (default = currently assigned value)
	  * @param removedAfter removed after to assign to the new model (default = currently assigned value)
	  * @return Copy of this model with the specified instruction targeting link properties
	  */
	protected def copyInstructionTargetingLink(id: Option[Int] = id, targetId: Option[Int] = targetId, 
		instructionId: Option[Int] = instructionId, created: Option[Instant] = created, 
		removedAfter: Option[Instant] = removedAfter): Repr
	
	
	// IMPLEMENTED	--------------------
	
	override def valueProperties = 
		Vector(dbProps.id.name -> id, dbProps.targetId.name -> targetId, 
			dbProps.instructionId.name -> instructionId, dbProps.created.name -> created, 
			dbProps.removedAfter.name -> removedAfter)
	
	/**
	  * @param created Time when this instruction targeting link was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copyInstructionTargetingLink(created = Some(created))
	
	override def withId(id: Int) = copyInstructionTargetingLink(id = Some(id))
	
	/**
	  * @param instructionId Id of the instruction applied to the linked entity
	  * @return A new copy of this model with the specified instruction id
	  */
	override def withInstructionId(instructionId: Int) = 
		copyInstructionTargetingLink(instructionId = Some(instructionId))
	
	/**
	  * @param removedAfter Time after which this link was removed
	  * @return A new copy of this model with the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = 
		copyInstructionTargetingLink(removedAfter = Some(removedAfter))
	
	/**
	  * @param targetId Id of the entity to which or where the linked instruction applies
	  * @return A new copy of this model with the specified target id
	  */
	override def withTargetId(targetId: Int) = copyInstructionTargetingLink(targetId = Some(targetId))
}

