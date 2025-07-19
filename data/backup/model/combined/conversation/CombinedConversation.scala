package vf.llama.model.combined.conversation

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.conversation.ConversationFactoryWrapper
import vf.llama.model.partial.conversation.ConversationData
import vf.llama.model.stored.conversation.Conversation

/**
  * Common trait for combinations that add additional data to conversations
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedConversation[+Repr] 
	extends Extender[ConversationData] with HasId[Int] with ConversationFactoryWrapper[Conversation, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped conversation
	  */
	def conversation: Conversation
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this conversation in the database
	  */
	override def id = conversation.id
	
	override def wrapped = conversation.data
	
	override protected def wrappedFactory = conversation
}

