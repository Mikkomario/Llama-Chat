package vf.llama.model.partial.instruction

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.time.Now

import java.time.Instant

object InstructionTargetingLinkData extends FromModelFactoryWithSchema[InstructionTargetingLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("targetId", IntType, Single("target_id")), 
			PropertyDeclaration("instructionId", IntType, Single("instruction_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("removedAfter", InstantType, Single("removed_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		InstructionTargetingLinkData(valid("targetId").getInt, valid("instructionId").getInt, 
			valid("created").getInstant, valid("removedAfter").instant)
	
	
	// OTHER	--------------------
	
	/**
	  * Creates a new instruction targeting link
	  * @param targetId Id of the entity to which or where the linked instruction applies
	  * @param instructionId Id of the instruction applied to the linked entity
	  * @param created Time when this instruction targeting link was added to the database
	  * @param removedAfter Time after which this link was removed
	  * @return instruction targeting link with the specified properties
	  */
	def apply(targetId: Int, instructionId: Int, created: Instant = Now, 
		removedAfter: Option[Instant] = None): InstructionTargetingLinkData = 
		_InstructionTargetingLinkData(targetId, instructionId, created, removedAfter)
	
	
	// NESTED	--------------------
	
	/**
	  * Concrete implementation of the instruction targeting link data trait
	  * @param targetId Id of the entity to which or where the linked instruction applies
	  * @param instructionId Id of the instruction applied to the linked entity
	  * @param created Time when this instruction targeting link was added to the database
	  * @param removedAfter Time after which this link was removed
	  * @author Mikko Hilpinen
	  * @since 01.09.2024
	  */
	private case class _InstructionTargetingLinkData(targetId: Int, instructionId: Int, 
		created: Instant = Now, removedAfter: Option[Instant] = None) 
		extends InstructionTargetingLinkData
	{
		// IMPLEMENTED	--------------------
		
		/**
		  * @param targetId Id of the entity to which or where the linked instruction applies
		  * @param instructionId Id of the instruction applied to the linked entity
		  * @param created Time when this instruction targeting link was added to the database
		  * @param removedAfter Time after which this link was removed
		  */
		override def copyInstructionTargetingLink(targetId: Int, instructionId: Int, created: Instant = Now, 
			removedAfter: Option[Instant] = None) = 
			_InstructionTargetingLinkData(targetId, instructionId, created, removedAfter)
	}
}

/**
  * Links an instruction to some entity to which / where it applies
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkData extends InstructionTargetingLinkDataLike[InstructionTargetingLinkData]
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this instruction targeting link has already been deprecated
	  */
	def isDeprecated = removedAfter.isDefined
	
	/**
	  * Whether this instruction targeting link is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
}

