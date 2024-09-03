package vf.llama.model.partial.color

import utopia.flow.collection.immutable.Empty
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.BooleanType
import utopia.flow.generic.model.mutable.DataType.DoubleType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.paradigm.angular.Angle
import vf.llama.model.factory.color.ColorFactory

object ColorData extends FromModelFactoryWithSchema[ColorData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("hue", DoubleType), PropertyDeclaration("saturation", 
			DoubleType, Empty, 1.0), PropertyDeclaration("lightness", DoubleType, Empty, 0.5), 
			PropertyDeclaration("predefined", BooleanType, Empty, false)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		ColorData(Angle.degrees(valid("hue").getDouble), valid("saturation").getDouble, 
			valid("lightness").getDouble, valid("predefined").getBoolean)
}

/**
  * Represents a color
  * @param hue Hue of this color, as an angle
  * @param saturation Saturation (color intensity) of this color
  * @param lightness Lightness of this color. Between 0 (black) and 1 (white).
  * @param predefined Whether this is a predefined color. False if this is a user-defined color.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ColorData(hue: Angle, saturation: Double = 1.0, lightness: Double = 0.5, 
	predefined: Boolean = false) 
	extends ColorFactory[ColorData] with ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("hue" -> hue.degrees, "saturation" -> saturation, "lightness" -> lightness, 
			"predefined" -> predefined))
	
	override def withHue(hue: Angle) = copy(hue = hue)
	
	override def withLightness(lightness: Double) = copy(lightness = lightness)
	
	override def withPredefined(predefined: Boolean) = copy(predefined = predefined)
	
	override def withSaturation(saturation: Double) = copy(saturation = saturation)
}

