package vf.llama.model.stored.meta

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.meta.field.DbSingleMetaInfoField
import vf.llama.model.factory.meta.MetaInfoFieldFactoryWrapper
import vf.llama.model.partial.meta.MetaInfoFieldData

object MetaInfoField extends StoredFromModelFactory[MetaInfoFieldData, MetaInfoField]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = MetaInfoFieldData
	
	override protected def complete(model: AnyModel, data: MetaInfoFieldData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a meta info field that has already been stored in the database
  * @param id id of this meta info field in the database
  * @param data Wrapped meta info field data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MetaInfoField(id: Int, data: MetaInfoFieldData) 
	extends StoredModelConvertible[MetaInfoFieldData] with FromIdFactory[Int, MetaInfoField] 
		with MetaInfoFieldFactoryWrapper[MetaInfoFieldData, MetaInfoField]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this meta info field in the database
	  */
	def access = DbSingleMetaInfoField(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: MetaInfoFieldData) = copy(data = data)
}

