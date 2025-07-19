package vf.llama.model.combined.conversation

import vf.llama.model.stored.conversation.{ConversationSummary, ConversationSummaryStatementLink}

object StatementLinkedConversationSummary
{
	// OTHER	--------------------
	
	/**
	  * @param summary summary to wrap
	  * @param link link to attach to this summary
	  * @return Combination of the specified summary and link
	  */
	def apply(summary: ConversationSummary, 
		link: Seq[ConversationSummaryStatementLink]): StatementLinkedConversationSummary = 
		_StatementLinkedConversationSummary(summary, link)
	
	
	// NESTED	--------------------
	
	/**
	  * @param summary summary to wrap
	  * @param link link to attach to this summary
	  */
	private case class _StatementLinkedConversationSummary(summary: ConversationSummary, 
		link: Seq[ConversationSummaryStatementLink]) 
		extends StatementLinkedConversationSummary
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: ConversationSummary) = copy(summary = factory)
	}
}

/**
  * Includes links to individual statements made within this summary
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkedConversationSummary 
	extends CombinedConversationSummary[StatementLinkedConversationSummary]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped conversation summary
	  */
	def summary: ConversationSummary
	
	/**
	  * Link that are attached to this summary
	  */
	def link: Seq[ConversationSummaryStatementLink]
	
	
	// IMPLEMENTED	--------------------
	
	override def conversationSummary = summary
}

