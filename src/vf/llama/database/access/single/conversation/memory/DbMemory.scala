package vf.llama.database.access.single.conversation.memory

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.MemoryDbFactory
import vf.llama.database.storable.conversation.MemoryDbModel
import vf.llama.model.stored.conversation.Memory

/**
  * Used for accessing individual memories
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMemory extends SingleRowModelAccess[Memory] with NonDeprecatedView[Memory] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = MemoryDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MemoryDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted memory
	  * @return An access point to that memory
	  */
	def apply(id: Int) = DbSingleMemory(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique memories.
	  * @return An access point to the memory that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueMemoryAccess(mergeCondition(additionalCondition))
}

