package vf.llama.model.combined.conversation

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.conversation.MessageFactoryWrapper
import vf.llama.model.partial.conversation.MessageData
import vf.llama.model.stored.conversation.Message

/**
  * Common trait for combinations that add additional data to messages
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedMessage[+Repr] 
	extends Extender[MessageData] with HasId[Int] with MessageFactoryWrapper[Message, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped message
	  */
	def message: Message
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this message in the database
	  */
	override def id = message.id
	
	override def wrapped = message.data
	
	override protected def wrappedFactory = message
}

