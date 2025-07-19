package vf.llama.database.factory.conversation

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.conversation.StatementLinkedMemory
import vf.llama.model.stored.conversation.{Memory, MemoryStatementLink}

/**
  * Used for reading statement linked memories from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object StatementLinkedMemoryDbFactory 
	extends MultiCombiningFactory[StatementLinkedMemory, Memory, MemoryStatementLink] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = MemoryStatementLinkDbFactory
	
	override def isAlwaysLinked = true
	
	override def nonDeprecatedCondition = parentFactory.nonDeprecatedCondition
	
	override def parentFactory = MemoryDbFactory
	
	/**
	  * @param memory memory to wrap
	  * @param link link to attach to this memory
	  */
	override def apply(memory: Memory, link: Seq[MemoryStatementLink]) = StatementLinkedMemory(memory, link)
}

