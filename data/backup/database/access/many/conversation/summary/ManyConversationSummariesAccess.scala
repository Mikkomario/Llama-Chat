package vf.llama.database.access.many.conversation.summary

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationSummaryDbFactory
import vf.llama.model.stored.conversation.ConversationSummary

object ManyConversationSummariesAccess extends ViewFactory[ManyConversationSummariesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyConversationSummariesAccess = 
		_ManyConversationSummariesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyConversationSummariesAccess(override val accessCondition: Option[Condition]) 
		extends ManyConversationSummariesAccess
}

/**
  * A common trait for access points which target multiple conversation summaries at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyConversationSummariesAccess 
	extends ManyConversationSummariesAccessLike[ConversationSummary, ManyConversationSummariesAccess] 
		with ManyRowModelAccess[ConversationSummary] 
		with ChronoRowFactoryView[ConversationSummary, ManyConversationSummariesAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationSummaryDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyConversationSummariesAccess = 
		ManyConversationSummariesAccess(condition)
}

