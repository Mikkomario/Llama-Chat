package vf.llama.model.partial.conversation

import utopia.echo.model.enumeration.ChatRole
import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactory
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.{ModelConvertible, ModelLike, Property}
import utopia.flow.time.Now
import vf.llama.model.factory.conversation.MessageFactory

import java.time.Instant

object MessageData extends FromModelFactory[MessageData]
{
	// ATTRIBUTES	--------------------
	
	lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("sessionId", IntType, Single("session_id")), 
			PropertyDeclaration("sender", IntType), PropertyDeclaration("created", InstantType, 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override def apply(model: ModelLike[Property]) = {
		schema.validate(model).flatMap { valid => 
			ChatRole.fromValue(valid("sender")).map { sender =>
				MessageData(valid("sessionId").getInt, sender, valid("created").getInstant)
			}
		}
	}
}

/**
  * Represents a single message from the user or from a persona
  * @param sessionId Id of the session during which this message was posted
  * @param sender Role of the entity that sent this message
  * @param created Time when this message was posted
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MessageData(sessionId: Int, sender: ChatRole, created: Instant = Now)
	extends MessageFactory[MessageData] with ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("sessionId" -> sessionId, "sender" -> sender.id, 
		"created" -> created))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withSender(sender: ChatRole) = copy(sender = sender)
	
	override def withSessionId(sessionId: Int) = copy(sessionId = sessionId)
}

