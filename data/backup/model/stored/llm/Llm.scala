package vf.llama.model.stored.llm

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.llm.DbSingleLlm
import vf.llama.model.factory.llm.LlmFactoryWrapper
import vf.llama.model.partial.llm.LlmData

object Llm extends StoredFromModelFactory[LlmData, Llm]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = LlmData
	
	override protected def complete(model: AnyModel, data: LlmData) = model("id").tryInt.map { apply(_, 
		data) }
}

/**
  * Represents a llm that has already been stored in the database
  * @param id id of this llm in the database
  * @param data Wrapped llm data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Llm(id: Int, data: LlmData) 
	extends StoredModelConvertible[LlmData] with FromIdFactory[Int, Llm] with LlmFactoryWrapper[LlmData, Llm]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this llm in the database
	  */
	def access = DbSingleLlm(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: LlmData) = copy(data = data)
}

