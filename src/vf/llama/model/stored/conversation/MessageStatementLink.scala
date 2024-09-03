package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.conversation.message.link.statement.DbSingleMessageStatementLink
import vf.llama.model.factory.conversation.MessageStatementLinkFactoryWrapper
import vf.llama.model.partial.conversation.MessageStatementLinkData
import vf.llama.model.partial.statement.StatementLinkData
import vf.llama.model.stored.statement.StoredStatementLinkLike

object MessageStatementLink extends StoredFromModelFactory[MessageStatementLinkData, MessageStatementLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = MessageStatementLinkData
	
	override protected def complete(model: AnyModel, data: MessageStatementLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a message statement link that has already been stored in the database
  * @param id id of this message statement link in the database
  * @param data Wrapped message statement link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MessageStatementLink(id: Int, data: MessageStatementLinkData) 
	extends MessageStatementLinkFactoryWrapper[MessageStatementLinkData, MessageStatementLink] 
		with StatementLinkData with StoredStatementLinkLike[MessageStatementLinkData, MessageStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this message statement link in the database
	  */
	def access = DbSingleMessageStatementLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: MessageStatementLinkData) = copy(data = data)
}

