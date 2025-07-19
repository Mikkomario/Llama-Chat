package vf.llama.database.access.many.conversation.session.summary.link.statement

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.many.statement.ManyStatementLinksAccessLike
import vf.llama.database.factory.conversation.SessionSummaryStatementLinkDbFactory
import vf.llama.database.storable.conversation.SessionSummaryStatementLinkDbModel
import vf.llama.model.stored.conversation.SessionSummaryStatementLink

object ManySessionSummaryStatementLinksAccess extends ViewFactory[ManySessionSummaryStatementLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManySessionSummaryStatementLinksAccess = 
		_ManySessionSummaryStatementLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManySessionSummaryStatementLinksAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManySessionSummaryStatementLinksAccess
}

/**
  * A common trait for access points which target multiple session summary statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManySessionSummaryStatementLinksAccess 
	extends ManyStatementLinksAccessLike[SessionSummaryStatementLink, ManySessionSummaryStatementLinksAccess] 
		with ManyRowModelAccess[SessionSummaryStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * session ids of the accessible session summary statement links
	  */
	def sessionIds(implicit connection: Connection) = parentIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = SessionSummaryStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = SessionSummaryStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManySessionSummaryStatementLinksAccess = 
		ManySessionSummaryStatementLinksAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param sessionIds Targeted session ids
	  * 
		@return Copy of this access point that only includes session summary statement links where session id is
	  *  within the specified value set
	  */
	def inSummaries(sessionIds: IterableOnce[Int]) = filter(model
		.sessionId.column.in(IntSet.from(sessionIds)))
	
	/**
	  * @param sessionId session id to target
	  * @return Copy of this access point that only includes session summary statement links 
	  * with the specified session id
	  */
	def inSummary(sessionId: Int) = filter(model.sessionId.column <=> sessionId)
}

