package vf.llama.model.combined.conversation

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.conversation.MemoryFactoryWrapper
import vf.llama.model.partial.conversation.MemoryData
import vf.llama.model.stored.conversation.Memory

/**
  * Common trait for combinations that add additional data to memories
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedMemory[+Repr] 
	extends Extender[MemoryData] with HasId[Int] with MemoryFactoryWrapper[Memory, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped memory
	  */
	def memory: Memory
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this memory in the database
	  */
	override def id = memory.id
	
	override def wrapped = memory.data
	
	override protected def wrappedFactory = memory
}

