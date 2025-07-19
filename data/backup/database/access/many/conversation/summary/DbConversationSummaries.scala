package vf.llama.database.access.many.conversation.summary

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.conversation.ConversationSummary

/**
  * The root access point when targeting multiple conversation summaries at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbConversationSummaries 
	extends ManyConversationSummariesAccess with NonDeprecatedView[ConversationSummary] 
		with ViewManyByIntIds[ManyConversationSummariesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) conversation summaries
	  */
	def includingHistory = DbConversationSummariesIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbConversationSummariesIncludingHistory extends ManyConversationSummariesAccess 
		with UnconditionalView
}

