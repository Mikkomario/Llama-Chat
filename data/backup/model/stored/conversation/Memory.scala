package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.conversation.memory.DbSingleMemory
import vf.llama.model.factory.conversation.MemoryFactoryWrapper
import vf.llama.model.partial.conversation.MemoryData

object Memory extends StoredFromModelFactory[MemoryData, Memory]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = MemoryData
	
	override protected def complete(model: AnyModel, data: MemoryData) = model("id").tryInt.map { apply(_, 
		data) }
}

/**
  * Represents a memory that has already been stored in the database
  * @param id id of this memory in the database
  * @param data Wrapped memory data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Memory(id: Int, data: MemoryData) 
	extends StoredModelConvertible[MemoryData] with FromIdFactory[Int, Memory] 
		with MemoryFactoryWrapper[MemoryData, Memory]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this memory in the database
	  */
	def access = DbSingleMemory(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: MemoryData) = copy(data = data)
}

