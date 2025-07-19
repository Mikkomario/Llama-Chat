package vf.llama.database.storable.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.parse.file.FileExtensions._
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.persona.PersonaAnimationFactory
import vf.llama.model.partial.persona.PersonaAnimationData
import vf.llama.model.stored.persona.PersonaAnimation

import scala.concurrent.duration.FiniteDuration

import java.nio.file.Path
import java.util.concurrent.TimeUnit

/**
  * 
	Used for constructing PersonaAnimationDbModel instances and for inserting persona animations to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaAnimationDbModel 
	extends StorableFactory[PersonaAnimationDbModel, PersonaAnimation, PersonaAnimationData] 
		with FromIdFactory[Int, PersonaAnimationDbModel] with HasIdProperty 
		with PersonaAnimationFactory[PersonaAnimationDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with set ids
	  */
	lazy val setId = property("setId")
	
	/**
	  * Database property used for interacting with relative sub directories
	  */
	lazy val relativeSubDirectory = property("relativeSubDirectory")
	
	/**
	  * Database property used for interacting with default frame durations
	  */
	lazy val defaultFrameDuration = property("defaultFrameDurationMillis")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.personaAnimation
	
	override def apply(data: PersonaAnimationData) = 
		apply(None, Some(data.setId), data.relativeSubDirectory.toJson, data.defaultFrameDuration)
	
	/**
	  * @param defaultFrameDuration Duration how long each frame will be shown by default. 
	  * None if the common default should be used instead.
	  * @return A model containing only the specified default frame duration
	  */
	override def withDefaultFrameDuration(defaultFrameDuration: FiniteDuration) = 
		apply(defaultFrameDuration = Some(defaultFrameDuration))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * 
		@param relativeSubDirectory Path to the directory that contains image files specific to this animation. 
	  * Relative to the image set's directory.
	  * @return A model containing only the specified relative sub directory
	  */
	override def withRelativeSubDirectory(relativeSubDirectory: Path) = 
		apply(relativeSubDirectory = relativeSubDirectory.toJson)
	
	/**
	  * @param setId Id of the set to which this animation belongs
	  * @return A model containing only the specified set id
	  */
	override def withSetId(setId: Int) = apply(setId = Some(setId))
	
	override protected def complete(id: Value, data: PersonaAnimationData) = PersonaAnimation(id.getInt, data)
}

/**
  * Used for interacting with PersonaAnimations in the database
  * @param id persona animation database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaAnimationDbModel(id: Option[Int] = None, setId: Option[Int] = None, 
	relativeSubDirectory: String = "", defaultFrameDuration: Option[FiniteDuration] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, PersonaAnimationDbModel] 
		with PersonaAnimationFactory[PersonaAnimationDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = PersonaAnimationDbModel.table
	
	override def valueProperties = 
		Vector(PersonaAnimationDbModel.id.name -> id, PersonaAnimationDbModel.setId.name -> setId, 
			PersonaAnimationDbModel.relativeSubDirectory.name -> relativeSubDirectory, 
			PersonaAnimationDbModel.defaultFrameDuration.name -> defaultFrameDuration.map { _.toUnit(TimeUnit.MILLISECONDS) })
	
	/**
	  * @param defaultFrameDuration Duration how long each frame will be shown by default. 
	  * None if the common default should be used instead.
	  * @return A new copy of this model with the specified default frame duration
	  */
	override def withDefaultFrameDuration(defaultFrameDuration: FiniteDuration) = 
		copy(defaultFrameDuration = Some(defaultFrameDuration))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * 
		@param relativeSubDirectory Path to the directory that contains image files specific to this animation. 
	  * Relative to the image set's directory.
	  * @return A new copy of this model with the specified relative sub directory
	  */
	override def withRelativeSubDirectory(relativeSubDirectory: Path) = 
		copy(relativeSubDirectory = relativeSubDirectory.toJson)
	
	/**
	  * @param setId Id of the set to which this animation belongs
	  * @return A new copy of this model with the specified set id
	  */
	override def withSetId(setId: Int) = copy(setId = Some(setId))
}

