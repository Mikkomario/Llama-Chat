package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.conversation.SessionFactory

import java.time.Instant

object SessionData extends FromModelFactoryWithSchema[SessionData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("conversationId", IntType, Single("conversation_id")), 
			PropertyDeclaration("started", InstantType, isOptional = true), PropertyDeclaration("ended", 
			InstantType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		SessionData(valid("conversationId").getInt, valid("started").getInstant, valid("ended").instant)
}

/**
  * Represents an individual user session within a conversation
  * @param conversationId Id of the conversation active during this session
  * @param started Time when this session started
  * @param ended Time when this session ended. None while active.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class SessionData(conversationId: Int, started: Instant = Now, ended: Option[Instant] = None) 
	extends SessionFactory[SessionData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this session has already been deprecated
	  */
	def isDeprecated = ended.isDefined
	
	/**
	  * Whether this session is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("conversationId" -> conversationId, "started" -> started, "ended" -> ended))
	
	override def withConversationId(conversationId: Int) = copy(conversationId = conversationId)
	
	override def withEnded(ended: Instant) = copy(ended = Some(ended))
	
	override def withStarted(started: Instant) = copy(started = started)
}

