package vf.llama.model.partial.instruction

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Model
import utopia.flow.generic.model.template.ModelConvertible

import java.time.Instant

/**
  * Common trait for classes which provide access to instruction targeting link properties
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait HasInstructionTargetingLinkProps extends ModelConvertible
{
	// ABSTRACT	--------------------
	
	/**
	  * Id of the entity to which or where the linked instruction applies
	  */
	def targetId: Int
	
	/**
	  * Id of the instruction applied to the linked entity
	  */
	def instructionId: Int
	
	/**
	  * Time when this instruction targeting link was added to the database
	  */
	def created: Instant
	
	/**
	  * Time after which this link was removed
	  */
	def removedAfter: Option[Instant]
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("targetId" -> targetId, "instructionId" -> instructionId, "created" -> created, 
			"removedAfter" -> removedAfter))
}

