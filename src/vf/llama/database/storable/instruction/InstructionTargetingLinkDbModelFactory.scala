package vf.llama.database.storable.instruction

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Table}
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.props.instruction.{InstructionTargetingLinkDbProps, InstructionTargetingLinkDbPropsWrapper}
import vf.llama.model.partial.instruction.InstructionTargetingLinkData
import vf.llama.model.stored.instruction.InstructionTargetingLink

import java.time.Instant

object InstructionTargetingLinkDbModelFactory
{
	// OTHER	--------------------
	
	/**
	  * @return A factory for constructing instruction targeting link database models
	  */
	def apply(table: Table, dbProps: InstructionTargetingLinkDbProps) = 
		InstructionTargetingLinkDbModelFactoryImpl(table, dbProps)
	
	
	// NESTED	--------------------
	
	/**
	  * 
		Used for constructing InstructionTargetingLinkDbModel instances and for inserting instruction targeting links
	  *  to the database
	  * @param table Table targeted by these models
	  * 
		@param instructionTargetingLinkDbProps Properties which specify how the database interactions are performed
	  * @author Mikko Hilpinen
	  * @since 01.09.2024, v0.1
	  */
	case class InstructionTargetingLinkDbModelFactoryImpl(table: Table, 
		instructionTargetingLinkDbProps: InstructionTargetingLinkDbProps) 
		extends InstructionTargetingLinkDbModelFactory with InstructionTargetingLinkDbPropsWrapper 
			with NullDeprecatable[InstructionTargetingLinkDbModel]
	{
		// ATTRIBUTES	--------------------
		
		override lazy val id = DbPropertyDeclaration("id", index)
		
		override val deprecationAttName = "removedAfter"
		
		
		// IMPLEMENTED	--------------------
		
		override def apply(data: InstructionTargetingLinkData) = 
			apply(None, Some(data.targetId), Some(data.instructionId), Some(data.created), data.removedAfter)
		
		/**
		  * @param created Time when this instruction targeting link was added to the database
		  * @return A model containing only the specified created
		  */
		override def withCreated(created: Instant) = apply(created = Some(created))
		
		override def withDeprecatedAfter(deprecationTime: Instant) = withRemovedAfter(deprecationTime)
		
		override def withId(id: Int) = apply(id = Some(id))
		
		/**
		  * @param instructionId Id of the instruction applied to the linked entity
		  * @return A model containing only the specified instruction id
		  */
		override def withInstructionId(instructionId: Int) = apply(instructionId = Some(instructionId))
		
		/**
		  * @param removedAfter Time after which this link was removed
		  * @return A model containing only the specified removed after
		  */
		override def withRemovedAfter(removedAfter: Instant) = apply(removedAfter = Some(removedAfter))
		
		/**
		  * @param targetId Id of the entity to which or where the linked instruction applies
		  * @return A model containing only the specified target id
		  */
		override def withTargetId(targetId: Int) = apply(targetId = Some(targetId))
		
		override protected def complete(id: Value, data: InstructionTargetingLinkData) = 
			InstructionTargetingLink(id.getInt, data)
		
		
		// OTHER	--------------------
		
		/**
		  * @param id instruction targeting link database id
		  * @return Constructs a new instruction targeting link database model with the specified properties
		  */
		def apply(id: Option[Int] = None, targetId: Option[Int] = None, instructionId: Option[Int] = None, 
			created: Option[Instant] = None, 
			removedAfter: Option[Instant] = None): InstructionTargetingLinkDbModel = 
			_InstructionTargetingLinkDbModel(table, instructionTargetingLinkDbProps, id, targetId, 
				instructionId, created, removedAfter)
	}
	
	/**
	  * Used for interacting with InstructionTargetingLinks in the database
	  * @param table Table interacted with when using this model
	  * @param dbProps Configurations of the interacted database properties
	  * @param id instruction targeting link database id
	  * @author Mikko Hilpinen
	  * @since 01.09.2024, v0.1
	  */
	private case class _InstructionTargetingLinkDbModel(table: Table, 
		dbProps: InstructionTargetingLinkDbProps, id: Option[Int] = None, targetId: Option[Int] = None, 
		instructionId: Option[Int] = None, created: Option[Instant] = None, 
		removedAfter: Option[Instant] = None) 
		extends InstructionTargetingLinkDbModel
	{
		// IMPLEMENTED	--------------------
		
		/**
		  * @param id Id to assign to the new model (default = currently assigned id)
		  * @param targetId target id to assign to the new model (default = currently assigned value)
		  * 
			@param instructionId instruction id to assign to the new model (default = currently assigned value)
		  * @param created created to assign to the new model (default = currently assigned value)
		  * @param removedAfter removed after to assign to the new model (default = currently assigned value)
		  * @return Copy of this model with the specified instruction targeting link properties
		  */
		override protected def copyInstructionTargetingLink(id: Option[Int] = id, 
			targetId: Option[Int] = targetId, instructionId: Option[Int] = instructionId, 
			created: Option[Instant] = created, removedAfter: Option[Instant] = removedAfter) = 
			copy(id = id, targetId = targetId, instructionId = instructionId, created = created, 
				removedAfter = removedAfter)
	}
}

/**
  * Common trait for factories yielding instruction targeting link database models
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkDbModelFactory 
	extends InstructionTargetingLinkDbModelFactoryLike[InstructionTargetingLinkDbModel, 
		InstructionTargetingLink, InstructionTargetingLinkData]

