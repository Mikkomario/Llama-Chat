package vf.llama.database.factory.conversation

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.conversation.StatementLinkedConversationSummary
import vf.llama.model.stored.conversation.{ConversationSummary, ConversationSummaryStatementLink}

/**
  * Used for reading statement linked conversation summaries from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object StatementLinkedConversationSummaryDbFactory 
	extends MultiCombiningFactory[StatementLinkedConversationSummary, ConversationSummary, ConversationSummaryStatementLink] 
		with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = ConversationSummaryStatementLinkDbFactory
	
	override def isAlwaysLinked = true
	
	override def nonDeprecatedCondition = parentFactory.nonDeprecatedCondition
	
	override def parentFactory = ConversationSummaryDbFactory
	
	/**
	  * @param summary summary to wrap
	  * @param link link to attach to this summary
	  */
	override def apply(summary: ConversationSummary, link: Seq[ConversationSummaryStatementLink]) = 
		StatementLinkedConversationSummary(summary, link)
}

