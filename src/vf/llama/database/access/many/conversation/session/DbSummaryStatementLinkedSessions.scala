package vf.llama.database.access.many.conversation.session

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.conversation.SummaryStatementLinkedSession

/**
  * The root access point when targeting multiple summary statement linked sessions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbSummaryStatementLinkedSessions 
	extends ManySummaryStatementLinkedSessionsAccess with NonDeprecatedView[SummaryStatementLinkedSession] 
		with ViewManyByIntIds[ManySummaryStatementLinkedSessionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * 
		A copy of this access point that includes historical (i.e. deprecated) summary statement linked sessions
	  */
	def includingHistory = DbSummaryStatementLinkedSessionsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbSummaryStatementLinkedSessionsIncludingHistory 
		extends ManySummaryStatementLinkedSessionsAccess with UnconditionalView
}

