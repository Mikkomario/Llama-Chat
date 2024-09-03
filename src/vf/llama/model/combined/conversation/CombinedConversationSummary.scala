package vf.llama.model.combined.conversation

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.conversation.ConversationSummaryFactoryWrapper
import vf.llama.model.partial.conversation.ConversationSummaryData
import vf.llama.model.stored.conversation.ConversationSummary

/**
  * Common trait for combinations that add additional data to conversation summaries
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedConversationSummary[+Repr] 
	extends Extender[ConversationSummaryData] with HasId[Int] 
		with ConversationSummaryFactoryWrapper[ConversationSummary, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped conversation summary
	  */
	def conversationSummary: ConversationSummary
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this conversation summary in the database
	  */
	override def id = conversationSummary.id
	
	override def wrapped = conversationSummary.data
	
	override protected def wrappedFactory = conversationSummary
}

