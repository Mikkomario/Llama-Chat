package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.conversation.session.summary.link.statement.DbSingleSessionSummaryStatementLink
import vf.llama.model.factory.conversation.SessionSummaryStatementLinkFactoryWrapper
import vf.llama.model.partial.conversation.SessionSummaryStatementLinkData
import vf.llama.model.partial.statement.StatementLinkData
import vf.llama.model.stored.statement.StoredStatementLinkLike

object SessionSummaryStatementLink 
	extends StoredFromModelFactory[SessionSummaryStatementLinkData, SessionSummaryStatementLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = SessionSummaryStatementLinkData
	
	override protected def complete(model: AnyModel, data: SessionSummaryStatementLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a session summary statement link that has already been stored in the database
  * @param id id of this session summary statement link in the database
  * @param data Wrapped session summary statement link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class SessionSummaryStatementLink(id: Int, data: SessionSummaryStatementLinkData) 
	extends SessionSummaryStatementLinkFactoryWrapper[SessionSummaryStatementLinkData, SessionSummaryStatementLink] 
		with StatementLinkData 
		with StoredStatementLinkLike[SessionSummaryStatementLinkData, SessionSummaryStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this session summary statement link in the database
	  */
	def access = DbSingleSessionSummaryStatementLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: SessionSummaryStatementLinkData) = copy(data = data)
}

