package vf.llama.model.stored.color

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.color.DbSingleColor
import vf.llama.model.factory.color.ColorFactoryWrapper
import vf.llama.model.partial.color.ColorData

object Color extends StoredFromModelFactory[ColorData, Color]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = ColorData
	
	override protected def complete(model: AnyModel, data: ColorData) = model("id").tryInt.map { apply(_, 
		data) }
}

/**
  * Represents a color that has already been stored in the database
  * @param id id of this color in the database
  * @param data Wrapped color data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Color(id: Int, data: ColorData) 
	extends StoredModelConvertible[ColorData] with FromIdFactory[Int, Color] 
		with ColorFactoryWrapper[ColorData, Color]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this color in the database
	  */
	def access = DbSingleColor(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: ColorData) = copy(data = data)
}

