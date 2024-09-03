package vf.llama.database.access.many.conversation.memory

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.conversation.StatementLinkedMemory

/**
  * The root access point when targeting multiple statement linked memories at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbStatementLinkedMemories 
	extends ManyStatementLinkedMemoriesAccess with NonDeprecatedView[StatementLinkedMemory] 
		with ViewManyByIntIds[ManyStatementLinkedMemoriesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) statement linked memories
	  */
	def includingHistory = DbStatementLinkedMemoriesIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbStatementLinkedMemoriesIncludingHistory 
		extends ManyStatementLinkedMemoriesAccess with UnconditionalView
}

