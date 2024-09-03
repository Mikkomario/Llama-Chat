package vf.llama.database.access.many.conversation.summary

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.conversation.StatementLinkedConversationSummary

/**
  * The root access point when targeting multiple statement linked conversation summaries at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbStatementLinkedConversationSummaries 
	extends ManyStatementLinkedConversationSummariesAccess 
		with NonDeprecatedView[StatementLinkedConversationSummary] 
		with ViewManyByIntIds[ManyStatementLinkedConversationSummariesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * 
		A copy of this access point that includes historical (i.e. deprecated) statement linked conversation summaries
	  */
	def includingHistory = DbStatementLinkedConversationSummariesIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbStatementLinkedConversationSummariesIncludingHistory 
		extends ManyStatementLinkedConversationSummariesAccess with UnconditionalView
}

