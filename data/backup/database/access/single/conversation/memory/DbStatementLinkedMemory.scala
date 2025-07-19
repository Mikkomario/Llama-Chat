package vf.llama.database.access.single.conversation.memory

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.StatementLinkedMemoryDbFactory
import vf.llama.database.storable.conversation.{MemoryDbModel, MemoryStatementLinkDbModel}
import vf.llama.model.combined.conversation.StatementLinkedMemory

/**
  * Used for accessing individual statement linked memories
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbStatementLinkedMemory 
	extends SingleModelAccess[StatementLinkedMemory] with NonDeprecatedView[StatementLinkedMemory] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = MemoryStatementLinkDbModel
	
	/**
	  * A database model (factory) used for interacting with linked memories
	  */
	private def model = MemoryDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedMemoryDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted statement linked memory
	  * @return An access point to that statement linked memory
	  */
	def apply(id: Int) = DbSingleStatementLinkedMemory(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique statement linked memories.
	  * @return An access point to the statement linked memory that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueStatementLinkedMemoryAccess(mergeCondition(additionalCondition))
}

