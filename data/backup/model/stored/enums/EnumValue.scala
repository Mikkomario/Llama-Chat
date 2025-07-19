package vf.llama.model.stored.enums

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.enums.value.DbSingleEnumValue
import vf.llama.model.factory.enums.EnumValueFactoryWrapper
import vf.llama.model.partial.enums.EnumValueData

object EnumValue extends StoredFromModelFactory[EnumValueData, EnumValue]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = EnumValueData
	
	override protected def complete(model: AnyModel, data: EnumValueData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a enum value that has already been stored in the database
  * @param id id of this enum value in the database
  * @param data Wrapped enum value data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumValue(id: Int, data: EnumValueData) 
	extends StoredModelConvertible[EnumValueData] with FromIdFactory[Int, EnumValue] 
		with EnumValueFactoryWrapper[EnumValueData, EnumValue]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this enum value in the database
	  */
	def access = DbSingleEnumValue(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: EnumValueData) = copy(data = data)
}

