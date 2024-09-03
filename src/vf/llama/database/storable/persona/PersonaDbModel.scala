package vf.llama.database.storable.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.persona.PersonaFactory
import vf.llama.model.partial.persona.PersonaData
import vf.llama.model.stored.persona.Persona

import java.time.Instant

/**
  * Used for constructing PersonaDbModel instances and for inserting personas to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaDbModel 
	extends StorableFactory[PersonaDbModel, Persona, PersonaData] with FromIdFactory[Int, PersonaDbModel] 
		with HasIdProperty with PersonaFactory[PersonaDbModel] with NullDeprecatable[PersonaDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with deleted afters
	  */
	lazy val deletedAfter = property("deletedAfter")
	
	override val deprecationAttName = "deletedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.persona
	
	override def apply(data: PersonaData) = apply(None, Some(data.created), data.deletedAfter)
	
	/**
	  * @param created Time when this persona was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deletedAfter Time when this persona was deleted
	  * @return A model containing only the specified deleted after
	  */
	override def withDeletedAfter(deletedAfter: Instant) = apply(deletedAfter = Some(deletedAfter))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withDeletedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	override protected def complete(id: Value, data: PersonaData) = Persona(id.getInt, data)
}

/**
  * Used for interacting with Personas in the database
  * @param id persona database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaDbModel(id: Option[Int] = None, created: Option[Instant] = None, 
	deletedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, PersonaDbModel] 
		with PersonaFactory[PersonaDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = PersonaDbModel.table
	
	override def valueProperties = 
		Vector(PersonaDbModel.id.name -> id, PersonaDbModel.created.name -> created, 
			PersonaDbModel.deletedAfter.name -> deletedAfter)
	
	/**
	  * @param created Time when this persona was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deletedAfter Time when this persona was deleted
	  * @return A new copy of this model with the specified deleted after
	  */
	override def withDeletedAfter(deletedAfter: Instant) = copy(deletedAfter = Some(deletedAfter))
	
	override def withId(id: Int) = copy(id = Some(id))
}

