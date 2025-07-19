package vf.llama.database.storable.color

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.paradigm.angular.Angle
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.color.ColorFactory
import vf.llama.model.partial.color.ColorData
import vf.llama.model.stored.color.Color

/**
  * Used for constructing ColorDbModel instances and for inserting colors to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ColorDbModel 
	extends StorableFactory[ColorDbModel, Color, ColorData] with FromIdFactory[Int, ColorDbModel] 
		with HasIdProperty with ColorFactory[ColorDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with hues
	  */
	lazy val hue = property("hueDegrees")
	
	/**
	  * Database property used for interacting with saturations
	  */
	lazy val saturation = property("saturation")
	
	/**
	  * Database property used for interacting with lightness values
	  */
	lazy val lightness = property("lightness")
	
	/**
	  * Database property used for interacting with are predefined
	  */
	lazy val predefined = property("predefined")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.color
	
	override def apply(data: ColorData) = 
		apply(None, Some(data.hue.degrees), Some(data.saturation), Some(data.lightness), 
			Some(data.predefined))
	
	/**
	  * @param hue Hue of this color, as an angle
	  * @return A model containing only the specified hue
	  */
	override def withHue(hue: Angle) = apply(hue = Some(hue.degrees))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param lightness Lightness of this color. Between 0 (black) and 1 (white).
	  * @return A model containing only the specified lightness
	  */
	override def withLightness(lightness: Double) = apply(lightness = Some(lightness))
	
	/**
	  * @param predefined Whether this is a predefined color. False if this is a user-defined color.
	  * @return A model containing only the specified predefined
	  */
	override def withPredefined(predefined: Boolean) = apply(predefined = Some(predefined))
	
	/**
	  * @param saturation Saturation (color intensity) of this color
	  * @return A model containing only the specified saturation
	  */
	override def withSaturation(saturation: Double) = apply(saturation = Some(saturation))
	
	override protected def complete(id: Value, data: ColorData) = Color(id.getInt, data)
}

/**
  * Used for interacting with Colors in the database
  * @param id color database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ColorDbModel(id: Option[Int] = None, hue: Option[Double] = None, 
	saturation: Option[Double] = None, lightness: Option[Double] = None, predefined: Option[Boolean] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, ColorDbModel] 
		with ColorFactory[ColorDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = ColorDbModel.table
	
	override def valueProperties = 
		Vector(ColorDbModel.id.name -> id, ColorDbModel.hue.name -> hue, 
			ColorDbModel.saturation.name -> saturation, ColorDbModel.lightness.name -> lightness, 
			ColorDbModel.predefined.name -> predefined)
	
	/**
	  * @param hue Hue of this color, as an angle
	  * @return A new copy of this model with the specified hue
	  */
	override def withHue(hue: Angle) = copy(hue = Some(hue.degrees))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param lightness Lightness of this color. Between 0 (black) and 1 (white).
	  * @return A new copy of this model with the specified lightness
	  */
	override def withLightness(lightness: Double) = copy(lightness = Some(lightness))
	
	/**
	  * @param predefined Whether this is a predefined color. False if this is a user-defined color.
	  * @return A new copy of this model with the specified predefined
	  */
	override def withPredefined(predefined: Boolean) = copy(predefined = Some(predefined))
	
	/**
	  * @param saturation Saturation (color intensity) of this color
	  * @return A new copy of this model with the specified saturation
	  */
	override def withSaturation(saturation: Double) = copy(saturation = Some(saturation))
}

