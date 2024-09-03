package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.conversation.thread.DbSingleThread
import vf.llama.model.factory.conversation.ThreadFactoryWrapper
import vf.llama.model.partial.conversation.ThreadData

object Thread extends StoredFromModelFactory[ThreadData, Thread]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = ThreadData
	
	override protected def complete(model: AnyModel, data: ThreadData) = model("id").tryInt.map { apply(_, 
		data) }
}

/**
  * Represents a thread that has already been stored in the database
  * @param id id of this thread in the database
  * @param data Wrapped thread data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Thread(id: Int, data: ThreadData) 
	extends StoredModelConvertible[ThreadData] with FromIdFactory[Int, Thread] 
		with ThreadFactoryWrapper[ThreadData, Thread]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this thread in the database
	  */
	def access = DbSingleThread(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: ThreadData) = copy(data = data)
}

