package vf.llama.database.access.many.conversation.memory.link.statement

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.many.statement.ManyStatementLinksAccessLike
import vf.llama.database.factory.conversation.MemoryStatementLinkDbFactory
import vf.llama.database.storable.conversation.MemoryStatementLinkDbModel
import vf.llama.model.stored.conversation.MemoryStatementLink

object ManyMemoryStatementLinksAccess extends ViewFactory[ManyMemoryStatementLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyMemoryStatementLinksAccess = 
		_ManyMemoryStatementLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyMemoryStatementLinksAccess(override val accessCondition: Option[Condition]) 
		extends ManyMemoryStatementLinksAccess
}

/**
  * A common trait for access points which target multiple memory statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyMemoryStatementLinksAccess 
	extends ManyStatementLinksAccessLike[MemoryStatementLink, ManyMemoryStatementLinksAccess] 
		with ManyRowModelAccess[MemoryStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * memory ids of the accessible memory statement links
	  */
	def memoryIds(implicit connection: Connection) = parentIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MemoryStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = MemoryStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyMemoryStatementLinksAccess = 
		ManyMemoryStatementLinksAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param memoryIds Targeted memory ids
	  * @return Copy of this access point that only includes memory statement links where memory id is within
	  *  the specified value set
	  */
	def inMemories(memoryIds: IterableOnce[Int]) = filter(model.memoryId.column.in(IntSet.from(memoryIds)))
	
	/**
	  * @param memoryId memory id to target
	  * @return Copy of this access point that only includes memory statement links 
		with the specified memory id
	  */
	def inMemory(memoryId: Int) = filter(model.memoryId.column <=> memoryId)
}

