package vf.llama.model.partial.persona

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.parse.file.FileExtensions._
import utopia.flow.time.Now
import vf.llama.model.factory.persona.PersonaImageSetFactory

import java.nio.file.Path
import java.time.Instant

object PersonaImageSetData extends FromModelFactoryWithSchema[PersonaImageSetData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("relativeDirectory", StringType, 
			Single("relative_directory")), PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("removedAfter", InstantType, Single("removed_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaImageSetData(valid("relativeDirectory").getString: Path, valid("created").getInstant, 
			valid("removedAfter").instant)
}

/**
  * Represents a set of images used for visualizing a Persona
  * @param relativeDirectory Path to the directory that contains the images used in this image set. 
  * Relative to the main image directory.
  * @param created Time when this persona image set was added to the database
  * @param removedAfter Time when this image set was removed or deleted
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaImageSetData(relativeDirectory: Path, created: Instant = Now, 
	removedAfter: Option[Instant] = None) 
	extends PersonaImageSetFactory[PersonaImageSetData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this persona image set has already been deprecated
	  */
	def isDeprecated = removedAfter.isDefined
	
	/**
	  * Whether this persona image set is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("relativeDirectory" -> relativeDirectory.toJson, "created" -> created, 
			"removedAfter" -> removedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withRelativeDirectory(relativeDirectory: Path) = copy(relativeDirectory = relativeDirectory)
	
	override def withRemovedAfter(removedAfter: Instant) = copy(removedAfter = Some(removedAfter))
}

