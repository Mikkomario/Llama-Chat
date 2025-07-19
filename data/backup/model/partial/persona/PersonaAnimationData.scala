package vf.llama.model.partial.persona

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.DurationType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.parse.file.FileExtensions._
import vf.llama.model.factory.persona.PersonaAnimationFactory

import scala.concurrent.duration.FiniteDuration

import java.nio.file.Path

object PersonaAnimationData extends FromModelFactoryWithSchema[PersonaAnimationData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("setId", IntType, Single("set_id")), 
			PropertyDeclaration("relativeSubDirectory", StringType, Single("relative_sub_directory"), 
			"".toJson), PropertyDeclaration("defaultFrameDuration", DurationType, 
			Single("default_frame_duration"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaAnimationData(valid("setId").getInt, valid("relativeSubDirectory").getString: Path, 
			valid("defaultFrameDuration").duration)
}

/**
  * Represents an individual animation representing some state of a Persona
  * @param setId Id of the set to which this animation belongs
  * @param relativeSubDirectory Path to the directory that contains image files specific to this animation. 
  * Relative to the image set's directory.
  * @param defaultFrameDuration Duration how long each frame will be shown by default. 
  * None if the common default should be used instead.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaAnimationData(setId: Int, relativeSubDirectory: Path = "", 
	defaultFrameDuration: Option[FiniteDuration] = None) 
	extends PersonaAnimationFactory[PersonaAnimationData] with ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("setId" -> setId, "relativeSubDirectory" -> relativeSubDirectory.toJson, 
			"defaultFrameDuration" -> defaultFrameDuration))
	
	override def withDefaultFrameDuration(defaultFrameDuration: FiniteDuration) = 
		copy(defaultFrameDuration = Some(defaultFrameDuration))
	
	override def withRelativeSubDirectory(relativeSubDirectory: Path) = 
		copy(relativeSubDirectory = relativeSubDirectory)
	
	override def withSetId(setId: Int) = copy(setId = setId)
}

