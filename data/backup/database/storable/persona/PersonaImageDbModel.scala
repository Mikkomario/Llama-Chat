package vf.llama.database.storable.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.persona.PersonaImageFactory
import vf.llama.model.partial.persona.PersonaImageData
import vf.llama.model.stored.persona.PersonaImage

import scala.concurrent.duration.FiniteDuration

import java.util.concurrent.TimeUnit

/**
  * Used for constructing PersonaImageDbModel instances and for inserting persona images to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaImageDbModel 
	extends StorableFactory[PersonaImageDbModel, PersonaImage, PersonaImageData] 
		with FromIdFactory[Int, PersonaImageDbModel] with HasIdProperty 
		with PersonaImageFactory[PersonaImageDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with animation ids
	  */
	lazy val animationId = property("animationId")
	
	/**
	  * Database property used for interacting with file names
	  */
	lazy val fileName = property("fileName")
	
	/**
	  * Database property used for interacting with order indexs
	  */
	lazy val orderIndex = property("orderIndex")
	
	/**
	  * Database property used for interacting with custom durations
	  */
	lazy val customDuration = property("customDurationMillis")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.personaImage
	
	override def apply(data: PersonaImageData) = 
		apply(None, Some(data.animationId), data.fileName, Some(data.orderIndex), data.customDuration)
	
	/**
	  * @param animationId Id of the animation this image is part of
	  * @return A model containing only the specified animation id
	  */
	override def withAnimationId(animationId: Int) = apply(animationId = Some(animationId))
	
	/**
	  * @param customDuration Duration how long this image / frame should be displayed at once. 
	  * None if the default duration should be applied.
	  * @return A model containing only the specified custom duration
	  */
	override
		 def withCustomDuration(customDuration: FiniteDuration) = apply(customDuration = Some(customDuration))
	
	/**
	  * @param fileName Name of the wrapped image file
	  * @return A model containing only the specified file name
	  */
	override def withFileName(fileName: String) = apply(fileName = fileName)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param orderIndex 0-based index of this image within its animation
	  * @return A model containing only the specified order index
	  */
	override def withOrderIndex(orderIndex: Int) = apply(orderIndex = Some(orderIndex))
	
	override protected def complete(id: Value, data: PersonaImageData) = PersonaImage(id.getInt, data)
}

/**
  * Used for interacting with PersonaImages in the database
  * @param id persona image database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaImageDbModel(id: Option[Int] = None, animationId: Option[Int] = None, 
	fileName: String = "", orderIndex: Option[Int] = None, customDuration: Option[FiniteDuration] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, PersonaImageDbModel] 
		with PersonaImageFactory[PersonaImageDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = PersonaImageDbModel.table
	
	override def valueProperties = 
		Vector(PersonaImageDbModel.id.name -> id, PersonaImageDbModel.animationId.name -> animationId, 
			PersonaImageDbModel.fileName.name -> fileName, PersonaImageDbModel.orderIndex.name -> orderIndex, 
			PersonaImageDbModel.customDuration.name -> customDuration.map { _.toUnit(TimeUnit.MILLISECONDS) })
	
	/**
	  * @param animationId Id of the animation this image is part of
	  * @return A new copy of this model with the specified animation id
	  */
	override def withAnimationId(animationId: Int) = copy(animationId = Some(animationId))
	
	/**
	  * @param customDuration Duration how long this image / frame should be displayed at once. 
	  * None if the default duration should be applied.
	  * @return A new copy of this model with the specified custom duration
	  */
	override
		 def withCustomDuration(customDuration: FiniteDuration) = copy(customDuration = Some(customDuration))
	
	/**
	  * @param fileName Name of the wrapped image file
	  * @return A new copy of this model with the specified file name
	  */
	override def withFileName(fileName: String) = copy(fileName = fileName)
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param orderIndex 0-based index of this image within its animation
	  * @return A new copy of this model with the specified order index
	  */
	override def withOrderIndex(orderIndex: Int) = copy(orderIndex = Some(orderIndex))
}

