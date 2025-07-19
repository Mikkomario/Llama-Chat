package vf.llama.database.storable.conversation

import utopia.echo.model.enumeration.ChatRole
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.conversation.MessageFactory
import vf.llama.model.partial.conversation.MessageData
import vf.llama.model.stored.conversation.Message

import java.time.Instant

/**
  * Used for constructing MessageDbModel instances and for inserting messages to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object MessageDbModel 
	extends StorableFactory[MessageDbModel, Message, MessageData] with FromIdFactory[Int, MessageDbModel] 
		with HasIdProperty with MessageFactory[MessageDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with session ids
	  */
	lazy val sessionId = property("sessionId")
	
	/**
	  * Database property used for interacting with senders
	  */
	lazy val sender = property("senderId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.message
	
	override def apply(data: MessageData) = 
		apply(None, Some(data.sessionId), Some(data.sender), Some(data.created))
	
	/**
	  * @param created Time when this message was posted
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param sender Role of the entity that sent this message
	  * @return A model containing only the specified sender
	  */
	override def withSender(sender: ChatRole) = apply(sender = Some(sender))
	
	/**
	  * @param sessionId Id of the session during which this message was posted
	  * @return A model containing only the specified session id
	  */
	override def withSessionId(sessionId: Int) = apply(sessionId = Some(sessionId))
	
	override protected def complete(id: Value, data: MessageData) = Message(id.getInt, data)
}

/**
  * Used for interacting with Messages in the database
  * @param id message database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MessageDbModel(id: Option[Int] = None, sessionId: Option[Int] = None, 
	sender: Option[ChatRole] = None, created: Option[Instant] = None)
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, MessageDbModel] 
		with MessageFactory[MessageDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = MessageDbModel.table
	
	override def valueProperties = 
		Vector(MessageDbModel.id.name -> id, MessageDbModel.sessionId.name -> sessionId, 
			MessageDbModel.sender.name -> sender.map[Value] { e => e.id }.getOrElse(Value.empty), 
			MessageDbModel.created.name -> created)
	
	/**
	  * @param created Time when this message was posted
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param sender Role of the entity that sent this message
	  * @return A new copy of this model with the specified sender
	  */
	override def withSender(sender: ChatRole) = copy(sender = Some(sender))
	
	/**
	  * @param sessionId Id of the session during which this message was posted
	  * @return A new copy of this model with the specified session id
	  */
	override def withSessionId(sessionId: Int) = copy(sessionId = Some(sessionId))
}

