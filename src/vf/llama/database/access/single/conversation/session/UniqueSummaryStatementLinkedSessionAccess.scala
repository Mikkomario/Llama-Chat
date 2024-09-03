package vf.llama.database.access.single.conversation.session

import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.SummaryStatementLinkedSessionDbFactory
import vf.llama.database.storable.conversation.SessionSummaryStatementLinkDbModel
import vf.llama.model.combined.conversation.SummaryStatementLinkedSession

object UniqueSummaryStatementLinkedSessionAccess 
	extends ViewFactory[UniqueSummaryStatementLinkedSessionAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueSummaryStatementLinkedSessionAccess = 
		_UniqueSummaryStatementLinkedSessionAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueSummaryStatementLinkedSessionAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniqueSummaryStatementLinkedSessionAccess
}

/**
  * A common trait for access points that return distinct summary statement linked sessions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueSummaryStatementLinkedSessionAccess 
	extends UniqueSessionAccessLike[SummaryStatementLinkedSession, UniqueSummaryStatementLinkedSessionAccess] 
		with NullDeprecatableView[UniqueSummaryStatementLinkedSessionAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked summary statement link
	  */
	protected def summaryStatementLinkModel = SessionSummaryStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = SummaryStatementLinkedSessionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueSummaryStatementLinkedSessionAccess = 
		UniqueSummaryStatementLinkedSessionAccess(condition)
}

