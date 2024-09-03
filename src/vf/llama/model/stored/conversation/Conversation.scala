package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.conversation.DbSingleConversation
import vf.llama.model.factory.conversation.ConversationFactoryWrapper
import vf.llama.model.partial.conversation.ConversationData

object Conversation extends StoredFromModelFactory[ConversationData, Conversation]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = ConversationData
	
	override protected def complete(model: AnyModel, data: ConversationData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a conversation that has already been stored in the database
  * @param id id of this conversation in the database
  * @param data Wrapped conversation data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Conversation(id: Int, data: ConversationData) 
	extends StoredModelConvertible[ConversationData] with FromIdFactory[Int, Conversation] 
		with ConversationFactoryWrapper[ConversationData, Conversation]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this conversation in the database
	  */
	def access = DbSingleConversation(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: ConversationData) = copy(data = data)
}

