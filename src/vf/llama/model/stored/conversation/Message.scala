package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.conversation.message.DbSingleMessage
import vf.llama.model.factory.conversation.MessageFactoryWrapper
import vf.llama.model.partial.conversation.MessageData

object Message extends StoredFromModelFactory[MessageData, Message]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = MessageData
	
	override protected def complete(model: AnyModel, data: MessageData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a message that has already been stored in the database
  * @param id id of this message in the database
  * @param data Wrapped message data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Message(id: Int, data: MessageData) 
	extends StoredModelConvertible[MessageData] with FromIdFactory[Int, Message] 
		with MessageFactoryWrapper[MessageData, Message]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this message in the database
	  */
	def access = DbSingleMessage(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: MessageData) = copy(data = data)
}

