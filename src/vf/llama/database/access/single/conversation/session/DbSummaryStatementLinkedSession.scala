package vf.llama.database.access.single.conversation.session

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.SummaryStatementLinkedSessionDbFactory
import vf.llama.database.storable.conversation.{SessionDbModel, SessionSummaryStatementLinkDbModel}
import vf.llama.model.combined.conversation.SummaryStatementLinkedSession

/**
  * Used for accessing individual summary statement linked sessions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbSummaryStatementLinkedSession 
	extends SingleModelAccess[SummaryStatementLinkedSession] 
		with NonDeprecatedView[SummaryStatementLinkedSession] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked summary statement link
	  */
	protected def summaryStatementLinkModel = SessionSummaryStatementLinkDbModel
	
	/**
	  * A database model (factory) used for interacting with linked sessions
	  */
	private def model = SessionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = SummaryStatementLinkedSessionDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted summary statement linked session
	  * @return An access point to that summary statement linked session
	  */
	def apply(id: Int) = DbSingleSummaryStatementLinkedSession(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique summary statement linked sessions.
	  * @return An access point to the summary statement linked session that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueSummaryStatementLinkedSessionAccess(mergeCondition(additionalCondition))
}

