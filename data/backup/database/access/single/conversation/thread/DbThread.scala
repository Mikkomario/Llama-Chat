package vf.llama.database.access.single.conversation.thread

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ThreadDbFactory
import vf.llama.database.storable.conversation.ThreadDbModel
import vf.llama.model.stored.conversation.Thread

/**
  * Used for accessing individual threads
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbThread extends SingleRowModelAccess[Thread] with NonDeprecatedView[Thread] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = ThreadDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ThreadDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted thread
	  * @return An access point to that thread
	  */
	def apply(id: Int) = DbSingleThread(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique threads.
	  * @return An access point to the thread that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueThreadAccess(mergeCondition(additionalCondition))
}

