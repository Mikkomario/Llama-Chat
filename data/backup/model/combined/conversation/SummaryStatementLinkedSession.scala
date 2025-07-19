package vf.llama.model.combined.conversation

import vf.llama.model.stored.conversation.{Session, SessionSummaryStatementLink}

object SummaryStatementLinkedSession
{
	// OTHER	--------------------
	
	/**
	  * @param session session to wrap
	  * @param summaryStatementLink summary statement link to attach to this session
	  * @return Combination of the specified session and summary statement link
	  */
	def apply(session: Session, 
		summaryStatementLink: Seq[SessionSummaryStatementLink]): SummaryStatementLinkedSession = 
		_SummaryStatementLinkedSession(session, summaryStatementLink)
	
	
	// NESTED	--------------------
	
	/**
	  * @param session session to wrap
	  * @param summaryStatementLink summary statement link to attach to this session
	  */
	private case class _SummaryStatementLinkedSession(session: Session, 
		summaryStatementLink: Seq[SessionSummaryStatementLink]) 
		extends SummaryStatementLinkedSession
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: Session) = copy(session = factory)
	}
}

/**
  * Includes links to statements which appear within this session's summary
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait SummaryStatementLinkedSession extends CombinedSession[SummaryStatementLinkedSession]
{
	// ABSTRACT	--------------------
	
	/**
	  * Summary statement link that are attached to this session
	  */
	def summaryStatementLink: Seq[SessionSummaryStatementLink]
}

