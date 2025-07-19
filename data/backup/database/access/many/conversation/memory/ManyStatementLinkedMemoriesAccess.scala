package vf.llama.database.access.many.conversation.memory

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.StatementLinkedMemoryDbFactory
import vf.llama.database.storable.conversation.MemoryStatementLinkDbModel
import vf.llama.model.combined.conversation.StatementLinkedMemory

object ManyStatementLinkedMemoriesAccess extends ViewFactory[ManyStatementLinkedMemoriesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyStatementLinkedMemoriesAccess = 
		_ManyStatementLinkedMemoriesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyStatementLinkedMemoriesAccess(override val accessCondition: Option[Condition]) 
		extends ManyStatementLinkedMemoriesAccess
}

/**
  * A common trait for access points that return multiple statement linked memories at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyStatementLinkedMemoriesAccess 
	extends ManyMemoriesAccessLike[StatementLinkedMemory, ManyStatementLinkedMemoriesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * memory ids of the accessible memory statement links
	  */
	def linkMemoryIds(implicit connection: Connection) = 
		pullColumn(linkModel.memoryId.column).map { v => v.getInt }
	
	/**
	  * statement ids of the accessible memory statement links
	  */
	def linkStatementIds(implicit connection: Connection) = 
		pullColumn(linkModel.statementId.column).map { v => v.getInt }
	
	/**
	  * order indices of the accessible memory statement links
	  */
	def linkOrderIndices(implicit connection: Connection) = 
		pullColumn(linkModel.orderIndex.column).map { v => v.getInt }
	
	/**
	  * Model (factory) used for interacting the memory statement links associated 
		with this statement linked memory
	  */
	protected def linkModel = MemoryStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedMemoryDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyStatementLinkedMemoriesAccess = 
		ManyStatementLinkedMemoriesAccess(condition)
}

