package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.conversation.summary.DbSingleConversationSummary
import vf.llama.model.factory.conversation.ConversationSummaryFactoryWrapper
import vf.llama.model.partial.conversation.ConversationSummaryData

object ConversationSummary extends StoredFromModelFactory[ConversationSummaryData, ConversationSummary]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = ConversationSummaryData
	
	override protected def complete(model: AnyModel, data: ConversationSummaryData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a conversation summary that has already been stored in the database
  * @param id id of this conversation summary in the database
  * @param data Wrapped conversation summary data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ConversationSummary(id: Int, data: ConversationSummaryData) 
	extends StoredModelConvertible[ConversationSummaryData] with FromIdFactory[Int, ConversationSummary] 
		with ConversationSummaryFactoryWrapper[ConversationSummaryData, ConversationSummary]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this conversation summary in the database
	  */
	def access = DbSingleConversationSummary(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: ConversationSummaryData) = copy(data = data)
}

