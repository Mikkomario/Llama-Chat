package vf.llama.database.factory.conversation

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.conversation.SummaryStatementLinkedSession
import vf.llama.model.stored.conversation.{Session, SessionSummaryStatementLink}

/**
  * Used for reading summary statement linked sessions from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object SummaryStatementLinkedSessionDbFactory 
	extends MultiCombiningFactory[SummaryStatementLinkedSession, Session, SessionSummaryStatementLink] 
		with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = SessionSummaryStatementLinkDbFactory
	
	override def isAlwaysLinked = false
	
	override def nonDeprecatedCondition = parentFactory.nonDeprecatedCondition
	
	override def parentFactory = SessionDbFactory
	
	/**
	  * @param session session to wrap
	  * @param summaryStatementLink summary statement link to attach to this session
	  */
	override def apply(session: Session, summaryStatementLink: Seq[SessionSummaryStatementLink]) = 
		SummaryStatementLinkedSession(session, summaryStatementLink)
}

