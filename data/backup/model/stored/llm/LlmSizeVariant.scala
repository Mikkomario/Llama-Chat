package vf.llama.model.stored.llm

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.llm.variant.DbSingleLlmSizeVariant
import vf.llama.model.factory.llm.LlmSizeVariantFactoryWrapper
import vf.llama.model.partial.llm.LlmSizeVariantData

object LlmSizeVariant extends StoredFromModelFactory[LlmSizeVariantData, LlmSizeVariant]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = LlmSizeVariantData
	
	override protected def complete(model: AnyModel, data: LlmSizeVariantData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a llm size variant that has already been stored in the database
  * @param id id of this llm size variant in the database
  * @param data Wrapped llm size variant data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class LlmSizeVariant(id: Int, data: LlmSizeVariantData) 
	extends StoredModelConvertible[LlmSizeVariantData] with FromIdFactory[Int, LlmSizeVariant] 
		with LlmSizeVariantFactoryWrapper[LlmSizeVariantData, LlmSizeVariant]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this llm size variant in the database
	  */
	def access = DbSingleLlmSizeVariant(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: LlmSizeVariantData) = copy(data = data)
}

