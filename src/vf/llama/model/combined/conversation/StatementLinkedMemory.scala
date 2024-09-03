package vf.llama.model.combined.conversation

import vf.llama.model.stored.conversation.{Memory, MemoryStatementLink}

object StatementLinkedMemory
{
	// OTHER	--------------------
	
	/**
	  * @param memory memory to wrap
	  * @param link link to attach to this memory
	  * @return Combination of the specified memory and link
	  */
	def apply(memory: Memory, link: Seq[MemoryStatementLink]): StatementLinkedMemory = 
		_StatementLinkedMemory(memory, link)
	
	
	// NESTED	--------------------
	
	/**
	  * @param memory memory to wrap
	  * @param link link to attach to this memory
	  */
	private case class _StatementLinkedMemory(memory: Memory, link: Seq[MemoryStatementLink]) 
		extends StatementLinkedMemory
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: Memory) = copy(memory = factory)
	}
}

/**
  * Includes links to statements made within this memory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkedMemory extends CombinedMemory[StatementLinkedMemory]
{
	// ABSTRACT	--------------------
	
	/**
	  * Link that are attached to this memory
	  */
	def link: Seq[MemoryStatementLink]
}

