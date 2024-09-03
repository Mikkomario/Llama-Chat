package vf.llama.model.partial.persona

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.DurationType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import vf.llama.model.factory.persona.PersonaImageFactory

import scala.concurrent.duration.FiniteDuration

object PersonaImageData extends FromModelFactoryWithSchema[PersonaImageData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("animationId", IntType, Single("animation_id")), 
			PropertyDeclaration("fileName", StringType, Single("file_name")), 
			PropertyDeclaration("orderIndex", IntType, Single("order_index"), 0), 
			PropertyDeclaration("customDuration", DurationType, Single("custom_duration"), 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaImageData(valid("animationId").getInt, valid("fileName").getString, 
			valid("orderIndex").getInt, valid("customDuration").duration)
}

/**
  * Represents an individual image / frame within a Persona animation
  * @param animationId Id of the animation this image is part of
  * @param fileName Name of the wrapped image file
  * @param orderIndex 0-based index of this image within its animation
  * @param customDuration Duration how long this image / frame should be displayed at once. 
  * None if the default duration should be applied.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaImageData(animationId: Int, fileName: String, orderIndex: Int = 0, 
	customDuration: Option[FiniteDuration] = None) 
	extends PersonaImageFactory[PersonaImageData] with ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("animationId" -> animationId, "fileName" -> fileName, "orderIndex" -> orderIndex, 
			"customDuration" -> customDuration))
	
	override def withAnimationId(animationId: Int) = copy(animationId = animationId)
	
	override
		 def withCustomDuration(customDuration: FiniteDuration) = copy(customDuration = Some(customDuration))
	
	override def withFileName(fileName: String) = copy(fileName = fileName)
	
	override def withOrderIndex(orderIndex: Int) = copy(orderIndex = orderIndex)
}

