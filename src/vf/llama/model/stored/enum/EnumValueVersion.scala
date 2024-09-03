package vf.llama.model.stored.`enum`

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.enum.value.version.DbSingleEnumValueVersion
import vf.llama.model.factory.enum.EnumValueVersionFactoryWrapper
import vf.llama.model.partial.enum.EnumValueVersionData

object EnumValueVersion extends StoredFromModelFactory[EnumValueVersionData, EnumValueVersion]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = EnumValueVersionData
	
	override protected def complete(model: AnyModel, data: EnumValueVersionData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a enum value version that has already been stored in the database
  * @param id id of this enum value version in the database
  * @param data Wrapped enum value version data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumValueVersion(id: Int, data: EnumValueVersionData) 
	extends StoredModelConvertible[EnumValueVersionData] with FromIdFactory[Int, EnumValueVersion] 
		with EnumValueVersionFactoryWrapper[EnumValueVersionData, EnumValueVersion]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this enum value version in the database
	  */
	def access = DbSingleEnumValueVersion(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: EnumValueVersionData) = copy(data = data)
}

