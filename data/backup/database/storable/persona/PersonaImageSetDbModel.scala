package vf.llama.database.storable.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.parse.file.FileExtensions._
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.persona.PersonaImageSetFactory
import vf.llama.model.partial.persona.PersonaImageSetData
import vf.llama.model.stored.persona.PersonaImageSet

import java.nio.file.Path
import java.time.Instant

/**
  * 
	Used for constructing PersonaImageSetDbModel instances and for inserting persona image sets to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaImageSetDbModel 
	extends StorableFactory[PersonaImageSetDbModel, PersonaImageSet, PersonaImageSetData] 
		with FromIdFactory[Int, PersonaImageSetDbModel] with HasIdProperty 
		with PersonaImageSetFactory[PersonaImageSetDbModel] with NullDeprecatable[PersonaImageSetDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with relative directories
	  */
	lazy val relativeDirectory = property("relativeDirectory")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with removed afters
	  */
	lazy val removedAfter = property("removedAfter")
	
	override val deprecationAttName = "removedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.personaImageSet
	
	override def apply(data: PersonaImageSetData) = 
		apply(None, data.relativeDirectory.toJson, Some(data.created), data.removedAfter)
	
	/**
	  * @param created Time when this persona image set was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withRemovedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param relativeDirectory Path to the directory that contains the images used in this image set. 
	  * Relative to the main image directory.
	  * @return A model containing only the specified relative directory
	  */
	override def withRelativeDirectory(relativeDirectory: Path) = 
		apply(relativeDirectory = relativeDirectory.toJson)
	
	/**
	  * @param removedAfter Time when this image set was removed or deleted
	  * @return A model containing only the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = apply(removedAfter = Some(removedAfter))
	
	override protected def complete(id: Value, data: PersonaImageSetData) = PersonaImageSet(id.getInt, data)
}

/**
  * Used for interacting with PersonaImageSets in the database
  * @param id persona image set database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaImageSetDbModel(id: Option[Int] = None, relativeDirectory: String = "", 
	created: Option[Instant] = None, removedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, PersonaImageSetDbModel] 
		with PersonaImageSetFactory[PersonaImageSetDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = PersonaImageSetDbModel.table
	
	override def valueProperties = 
		Vector(PersonaImageSetDbModel.id.name -> id, 
			PersonaImageSetDbModel.relativeDirectory.name -> relativeDirectory, 
			PersonaImageSetDbModel.created.name -> created, 
			PersonaImageSetDbModel.removedAfter.name -> removedAfter)
	
	/**
	  * @param created Time when this persona image set was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param relativeDirectory Path to the directory that contains the images used in this image set. 
	  * Relative to the main image directory.
	  * @return A new copy of this model with the specified relative directory
	  */
	override def withRelativeDirectory(relativeDirectory: Path) = 
		copy(relativeDirectory = relativeDirectory.toJson)
	
	/**
	  * @param removedAfter Time when this image set was removed or deleted
	  * @return A new copy of this model with the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = copy(removedAfter = Some(removedAfter))
}

