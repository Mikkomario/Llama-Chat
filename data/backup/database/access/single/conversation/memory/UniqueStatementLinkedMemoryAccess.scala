package vf.llama.database.access.single.conversation.memory

import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.StatementLinkedMemoryDbFactory
import vf.llama.database.storable.conversation.MemoryStatementLinkDbModel
import vf.llama.model.combined.conversation.StatementLinkedMemory

object UniqueStatementLinkedMemoryAccess extends ViewFactory[UniqueStatementLinkedMemoryAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueStatementLinkedMemoryAccess = 
		_UniqueStatementLinkedMemoryAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueStatementLinkedMemoryAccess(override val accessCondition: Option[Condition]) 
		extends UniqueStatementLinkedMemoryAccess
}

/**
  * A common trait for access points that return distinct statement linked memories
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueStatementLinkedMemoryAccess 
	extends UniqueMemoryAccessLike[StatementLinkedMemory, UniqueStatementLinkedMemoryAccess] 
		with NullDeprecatableView[UniqueStatementLinkedMemoryAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = MemoryStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedMemoryDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueStatementLinkedMemoryAccess = 
		UniqueStatementLinkedMemoryAccess(condition)
}

