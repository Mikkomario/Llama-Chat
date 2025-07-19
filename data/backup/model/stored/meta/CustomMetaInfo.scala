package vf.llama.model.stored.meta

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.meta.custom.DbSingleCustomMetaInfo
import vf.llama.model.factory.meta.CustomMetaInfoFactoryWrapper
import vf.llama.model.partial.meta.CustomMetaInfoData

object CustomMetaInfo extends StoredFromModelFactory[CustomMetaInfoData, CustomMetaInfo]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = CustomMetaInfoData
	
	override protected def complete(model: AnyModel, data: CustomMetaInfoData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a custom meta info that has already been stored in the database
  * @param id id of this custom meta info in the database
  * @param data Wrapped custom meta info data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class CustomMetaInfo(id: Int, data: CustomMetaInfoData) 
	extends StoredModelConvertible[CustomMetaInfoData] with FromIdFactory[Int, CustomMetaInfo] 
		with CustomMetaInfoFactoryWrapper[CustomMetaInfoData, CustomMetaInfo]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this custom meta info in the database
	  */
	def access = DbSingleCustomMetaInfo(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: CustomMetaInfoData) = copy(data = data)
}

