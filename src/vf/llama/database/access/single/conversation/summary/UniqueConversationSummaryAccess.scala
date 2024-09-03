package vf.llama.database.access.single.conversation.summary

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationSummaryDbFactory
import vf.llama.model.stored.conversation.ConversationSummary

object UniqueConversationSummaryAccess extends ViewFactory[UniqueConversationSummaryAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueConversationSummaryAccess = 
		_UniqueConversationSummaryAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueConversationSummaryAccess(override val accessCondition: Option[Condition]) 
		extends UniqueConversationSummaryAccess
}

/**
  * A common trait for access points that return individual and distinct conversation summaries.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueConversationSummaryAccess 
	extends UniqueConversationSummaryAccessLike[ConversationSummary, UniqueConversationSummaryAccess] 
		with SingleChronoRowModelAccess[ConversationSummary, UniqueConversationSummaryAccess] 
		with NullDeprecatableView[UniqueConversationSummaryAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationSummaryDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueConversationSummaryAccess = 
		UniqueConversationSummaryAccess(condition)
}

