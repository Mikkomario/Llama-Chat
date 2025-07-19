package vf.llama.database.access.single.conversation.memory.link.statement

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.single.statement.UniqueStatementLinkAccessLike
import vf.llama.database.factory.conversation.MemoryStatementLinkDbFactory
import vf.llama.database.storable.conversation.MemoryStatementLinkDbModel
import vf.llama.model.stored.conversation.MemoryStatementLink

object UniqueMemoryStatementLinkAccess extends ViewFactory[UniqueMemoryStatementLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueMemoryStatementLinkAccess = 
		_UniqueMemoryStatementLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueMemoryStatementLinkAccess(override val accessCondition: Option[Condition]) 
		extends UniqueMemoryStatementLinkAccess
}

/**
  * A common trait for access points that return individual and distinct memory statement links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueMemoryStatementLinkAccess 
	extends UniqueStatementLinkAccessLike[MemoryStatementLink, UniqueMemoryStatementLinkAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the memory where the linked statement appears. 
	  * None if no memory statement link (or value) was found.
	  */
	def memoryId(implicit connection: Connection) = parentId
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MemoryStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = MemoryStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueMemoryStatementLinkAccess = 
		UniqueMemoryStatementLinkAccess(condition)
}

