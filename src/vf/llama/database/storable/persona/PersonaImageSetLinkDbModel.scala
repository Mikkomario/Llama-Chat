package vf.llama.database.storable.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.persona.PersonaImageSetLinkFactory
import vf.llama.model.partial.persona.PersonaImageSetLinkData
import vf.llama.model.stored.persona.PersonaImageSetLink

import java.time.Instant

/**
  * Used for constructing PersonaImageSetLinkDbModel instances and for inserting persona image set links to
  *  the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaImageSetLinkDbModel 
	extends StorableFactory[PersonaImageSetLinkDbModel, PersonaImageSetLink, PersonaImageSetLinkData] 
		with FromIdFactory[Int, PersonaImageSetLinkDbModel] with HasIdProperty 
		with PersonaImageSetLinkFactory[PersonaImageSetLinkDbModel] 
		with NullDeprecatable[PersonaImageSetLinkDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with persona ids
	  */
	lazy val personaId = property("personaId")
	
	/**
	  * Database property used for interacting with image set ids
	  */
	lazy val imageSetId = property("imageSetId")
	
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
	
	override def table = LlamaChatTables.personaImageSetLink
	
	override def apply(data: PersonaImageSetLinkData) = 
		apply(None, Some(data.personaId), Some(data.imageSetId), Some(data.created), data.removedAfter)
	
	/**
	  * @param created Time when this persona image set link was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withRemovedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param imageSetId Id of the image set used for the linked persona
	  * @return A model containing only the specified image set id
	  */
	override def withImageSetId(imageSetId: Int) = apply(imageSetId = Some(imageSetId))
	
	/**
	  * @param personaId Id of the persona for which the linked images are used
	  * @return A model containing only the specified persona id
	  */
	override def withPersonaId(personaId: Int) = apply(personaId = Some(personaId))
	
	/**
	  * @param removedAfter Time when this link was removed
	  * @return A model containing only the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = apply(removedAfter = Some(removedAfter))
	
	override protected def complete(id: Value, data: PersonaImageSetLinkData) = 
		PersonaImageSetLink(id.getInt, data)
}

/**
  * Used for interacting with PersonaImageSetLinks in the database
  * @param id persona image set link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaImageSetLinkDbModel(id: Option[Int] = None, personaId: Option[Int] = None, 
	imageSetId: Option[Int] = None, created: Option[Instant] = None, removedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, PersonaImageSetLinkDbModel] 
		with PersonaImageSetLinkFactory[PersonaImageSetLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = PersonaImageSetLinkDbModel.table
	
	override def valueProperties = 
		Vector(PersonaImageSetLinkDbModel.id.name -> id, 
			PersonaImageSetLinkDbModel.personaId.name -> personaId, 
			PersonaImageSetLinkDbModel.imageSetId.name -> imageSetId, 
			PersonaImageSetLinkDbModel.created.name -> created, 
			PersonaImageSetLinkDbModel.removedAfter.name -> removedAfter)
	
	/**
	  * @param created Time when this persona image set link was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param imageSetId Id of the image set used for the linked persona
	  * @return A new copy of this model with the specified image set id
	  */
	override def withImageSetId(imageSetId: Int) = copy(imageSetId = Some(imageSetId))
	
	/**
	  * @param personaId Id of the persona for which the linked images are used
	  * @return A new copy of this model with the specified persona id
	  */
	override def withPersonaId(personaId: Int) = copy(personaId = Some(personaId))
	
	/**
	  * @param removedAfter Time when this link was removed
	  * @return A new copy of this model with the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = copy(removedAfter = Some(removedAfter))
}

