package vf.llama.database.access.many.conversation.session

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.SummaryStatementLinkedSessionDbFactory
import vf.llama.database.storable.conversation.SessionSummaryStatementLinkDbModel
import vf.llama.model.combined.conversation.SummaryStatementLinkedSession

object ManySummaryStatementLinkedSessionsAccess extends ViewFactory[ManySummaryStatementLinkedSessionsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManySummaryStatementLinkedSessionsAccess = 
		_ManySummaryStatementLinkedSessionsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManySummaryStatementLinkedSessionsAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManySummaryStatementLinkedSessionsAccess
}

/**
  * A common trait for access points that return multiple summary statement linked sessions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManySummaryStatementLinkedSessionsAccess 
	extends ManySessionsAccessLike[SummaryStatementLinkedSession, ManySummaryStatementLinkedSessionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * session ids of the accessible session summary statement links
	  */
	def summaryStatementLinkSessionIds(implicit connection: Connection) = 
		pullColumn(summaryStatementLinkModel.sessionId.column).map { v => v.getInt }
	
	/**
	  * statement ids of the accessible session summary statement links
	  */
	def summaryStatementLinkStatementIds(implicit connection: Connection) = 
		pullColumn(summaryStatementLinkModel.statementId.column).map { v => v.getInt }
	
	/**
	  * order indices of the accessible session summary statement links
	  */
	def summaryStatementLinkOrderIndices(implicit connection: Connection) = 
		pullColumn(summaryStatementLinkModel.orderIndex.column).map { v => v.getInt }
	
	/**
	  * Model (factory) used for interacting the session summary statement links associated 
	  * with this summary statement linked session
	  */
	protected def summaryStatementLinkModel = SessionSummaryStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = SummaryStatementLinkedSessionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManySummaryStatementLinkedSessionsAccess = 
		ManySummaryStatementLinkedSessionsAccess(condition)
}

