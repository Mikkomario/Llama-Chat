package vf.llama.database.access.single.conversation.session.summary.link.statement

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.statement.UniqueStatementLinkAccessLike
import vf.llama.database.factory.conversation.SessionSummaryStatementLinkDbFactory
import vf.llama.database.storable.conversation.SessionSummaryStatementLinkDbModel
import vf.llama.model.stored.conversation.SessionSummaryStatementLink

object UniqueSessionSummaryStatementLinkAccess extends ViewFactory[UniqueSessionSummaryStatementLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueSessionSummaryStatementLinkAccess = 
		_UniqueSessionSummaryStatementLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueSessionSummaryStatementLinkAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniqueSessionSummaryStatementLinkAccess
}

/**
  * A common trait for access points that return individual and distinct session summary statement links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueSessionSummaryStatementLinkAccess 
	extends UniqueStatementLinkAccessLike[SessionSummaryStatementLink, 
		UniqueSessionSummaryStatementLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the session being summarized. 
	  * None if no session summary statement link (or value) was found.
	  */
	def sessionId(implicit connection: Connection) = parentId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = SessionSummaryStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = SessionSummaryStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueSessionSummaryStatementLinkAccess = 
		UniqueSessionSummaryStatementLinkAccess(condition)
}

