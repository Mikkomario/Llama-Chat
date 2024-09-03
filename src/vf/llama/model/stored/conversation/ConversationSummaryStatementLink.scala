package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.conversation.summary.link.statement.DbSingleConversationSummaryStatementLink
import vf.llama.model.factory.conversation.ConversationSummaryStatementLinkFactoryWrapper
import vf.llama.model.partial.conversation.ConversationSummaryStatementLinkData
import vf.llama.model.partial.statement.StatementLinkData
import vf.llama.model.stored.statement.StoredStatementLinkLike

object ConversationSummaryStatementLink 
	extends StoredFromModelFactory[ConversationSummaryStatementLinkData, ConversationSummaryStatementLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = ConversationSummaryStatementLinkData
	
	override protected def complete(model: AnyModel, data: ConversationSummaryStatementLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a conversation summary statement link that has already been stored in the database
  * @param id id of this conversation summary statement link in the database
  * @param data Wrapped conversation summary statement link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ConversationSummaryStatementLink(id: Int, data: ConversationSummaryStatementLinkData) 
	extends ConversationSummaryStatementLinkFactoryWrapper[ConversationSummaryStatementLinkData, ConversationSummaryStatementLink] 
		with StatementLinkData 
		with StoredStatementLinkLike[ConversationSummaryStatementLinkData, ConversationSummaryStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this conversation summary statement link in the database
	  */
	def access = DbSingleConversationSummaryStatementLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: ConversationSummaryStatementLinkData) = copy(data = data)
}

