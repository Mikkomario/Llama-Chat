package vf.llama.database.storable.instruction

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.instruction.InstructionTargetingLinkDbProps
import vf.llama.model.factory.instruction.TopicInstructionLinkFactory
import vf.llama.model.partial.instruction.TopicInstructionLinkData
import vf.llama.model.stored.instruction.TopicInstructionLink

import java.time.Instant

/**
  * Used for constructing TopicInstructionLinkDbModel instances and for inserting topic instruction links
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object TopicInstructionLinkDbModel 
	extends InstructionTargetingLinkDbModelFactoryLike[TopicInstructionLinkDbModel, TopicInstructionLink, TopicInstructionLinkData] 
		with TopicInstructionLinkFactory[TopicInstructionLinkDbModel] with InstructionTargetingLinkDbProps 
		with NullDeprecatable[TopicInstructionLinkDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with topic ids
	  */
	lazy val topicId = property("topicId")
	
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
	
	override def table = LlamaChatTables.topicInstructionLink
	
	override def targetId = topicId
	
	override def apply(data: TopicInstructionLinkData) = 
		apply(None, Some(data.topicId), Some(data.instructionId), Some(data.created), data.removedAfter)
	
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
	  * @param topicId Id of the topic where the linked instruction applies
	  * @return A model containing only the specified topic id
	  */
	override def withTopicId(topicId: Int) = apply(topicId = Some(topicId))
	
	override protected def complete(id: Value, data: TopicInstructionLinkData) = 
		TopicInstructionLink(id.getInt, data)
}

/**
  * Used for interacting with TopicInstructionLinks in the database
  * @param id topic instruction link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicInstructionLinkDbModel(id: Option[Int] = None, topicId: Option[Int] = None, 
	instructionId: Option[Int] = None, created: Option[Instant] = None, removedAfter: Option[Instant] = None) 
	extends InstructionTargetingLinkDbModel 
		with InstructionTargetingLinkDbModelLike[TopicInstructionLinkDbModel] 
		with TopicInstructionLinkFactory[TopicInstructionLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = TopicInstructionLinkDbModel
	
	override def table = TopicInstructionLinkDbModel.table
	
	override def targetId = topicId
	
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
		copy(id = id, topicId = targetId, instructionId = instructionId, created = created, 
			removedAfter = removedAfter)
	
	/**
	  * @param topicId Id of the topic where the linked instruction applies
	  * @return A new copy of this model with the specified topic id
	  */
	override def withTopicId(topicId: Int) = copy(topicId = Some(topicId))
}

