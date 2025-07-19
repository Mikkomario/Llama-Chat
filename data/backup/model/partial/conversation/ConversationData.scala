package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.conversation.ConversationFactory

import java.time.Instant

object ConversationData extends FromModelFactoryWithSchema[ConversationData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("threadId", IntType, Single("thread_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("closedAfter", InstantType, Single("closed_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		ConversationData(valid("threadId").getInt, valid("created").getInstant, valid("closedAfter").instant)
}

/**
  * Represents an individual conversation with a persona
  * @param threadId Id of the thread this conversation is part of
  * @param created Time when this conversation was added to the database
  * @param closedAfter Time when this conversation was closed the last time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ConversationData(threadId: Int, created: Instant = Now, closedAfter: Option[Instant] = None) 
	extends ConversationFactory[ConversationData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this conversation has already been deprecated
	  */
	def isDeprecated = closedAfter.isDefined
	
	/**
	  * Whether this conversation is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("threadId" -> threadId, "created" -> created, "closedAfter" -> closedAfter))
	
	override def withClosedAfter(closedAfter: Instant) = copy(closedAfter = Some(closedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withThreadId(threadId: Int) = copy(threadId = threadId)
}

