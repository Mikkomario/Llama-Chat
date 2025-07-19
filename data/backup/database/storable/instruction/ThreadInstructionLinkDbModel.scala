package vf.llama.database.storable.instruction

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.instruction.InstructionTargetingLinkDbProps
import vf.llama.model.factory.instruction.ThreadInstructionLinkFactory
import vf.llama.model.partial.instruction.ThreadInstructionLinkData
import vf.llama.model.stored.instruction.ThreadInstructionLink

import java.time.Instant

/**
  * Used for constructing ThreadInstructionLinkDbModel instances and for inserting thread instruction links
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ThreadInstructionLinkDbModel 
	extends InstructionTargetingLinkDbModelFactoryLike[ThreadInstructionLinkDbModel, ThreadInstructionLink, ThreadInstructionLinkData] 
		with ThreadInstructionLinkFactory[ThreadInstructionLinkDbModel] with InstructionTargetingLinkDbProps 
		with NullDeprecatable[ThreadInstructionLinkDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with thread ids
	  */
	lazy val threadId = property("threadId")
	
	/**
	  * Database property used for interacting with instruction ids
	  */
	override lazy val instructionId = property("instructionId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	override lazy val created = property("created")
	
	/**
	  * Database property used for interacting with remove times
	  */
	override lazy val removedAfter = property("removedAfter")
	
	override val deprecationAttName = "removedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.threadInstructionLink
	
	override def targetId = threadId
	
	override def apply(data: ThreadInstructionLinkData) = 
		apply(None, Some(data.threadId), Some(data.instructionId), Some(data.created), data.removedAfter)
	
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
	  * @param threadId Id of the thread where the linked instruction applies
	  * @return A model containing only the specified thread id
	  */
	override def withThreadId(threadId: Int) = apply(threadId = Some(threadId))
	
	override protected def complete(id: Value, data: ThreadInstructionLinkData) = 
		ThreadInstructionLink(id.getInt, data)
}

/**
  * Used for interacting with ThreadInstructionLinks in the database
  * @param id thread instruction link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ThreadInstructionLinkDbModel(id: Option[Int] = None, threadId: Option[Int] = None, 
	instructionId: Option[Int] = None, created: Option[Instant] = None, removedAfter: Option[Instant] = None) 
	extends InstructionTargetingLinkDbModel 
		with InstructionTargetingLinkDbModelLike[ThreadInstructionLinkDbModel] 
		with ThreadInstructionLinkFactory[ThreadInstructionLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = ThreadInstructionLinkDbModel
	
	override def table = ThreadInstructionLinkDbModel.table
	
	override def targetId = threadId
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param targetId target id to assign to the new model (default = currently assigned value)
	  * @param instructionId instruction id to assign to the new model (default = currently assigned value)
	  * @param created created to assign to the new model (default = currently assigned value)
	  * @param removedAfter removed after to assign to the new model (default = currently assigned value)
	  */
	override def copyInstructionTargetingLink(id: Option[Int] = id, targetId: Option[Int] = targetId, 
		instructionId: Option[Int] = instructionId, created: Option[Instant] = created, 
		removedAfter: Option[Instant] = removedAfter) = 
		copy(id = id, threadId = targetId, instructionId = instructionId, created = created, 
			removedAfter = removedAfter)
	
	/**
	  * @param threadId Id of the thread where the linked instruction applies
	  * @return A new copy of this model with the specified thread id
	  */
	override def withThreadId(threadId: Int) = copy(threadId = Some(threadId))
}

