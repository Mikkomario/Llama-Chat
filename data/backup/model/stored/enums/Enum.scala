package vf.llama.model.stored.enums

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.enums.DbSingleEnum
import vf.llama.model.factory.enums.EnumFactoryWrapper
import vf.llama.model.partial.enums.EnumData

object Enum extends StoredFromModelFactory[EnumData, Enum]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = EnumData
	
	override protected def complete(model: AnyModel, data: EnumData) = model("id").tryInt.map { apply(_, 
		data) }
}

/**
  * Represents a enum that has already been stored in the database
  * @param id id of this enum in the database
  * @param data Wrapped enum data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Enum(id: Int, data: EnumData) 
	extends StoredModelConvertible[EnumData] with FromIdFactory[Int, Enum] 
		with EnumFactoryWrapper[EnumData, Enum]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this enum in the database
	  */
	def access = DbSingleEnum(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: EnumData) = copy(data = data)
}

