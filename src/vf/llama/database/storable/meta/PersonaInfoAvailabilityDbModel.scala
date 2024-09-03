package vf.llama.database.storable.meta

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.meta.MetaInfoAvailabilityDbProps
import vf.llama.model.factory.meta.PersonaInfoAvailabilityFactory
import vf.llama.model.partial.meta.PersonaInfoAvailabilityData
import vf.llama.model.stored.meta.PersonaInfoAvailability

import java.time.Instant

/**
  * 
	Used for constructing PersonaInfoAvailabilityDbModel instances and for inserting persona info availabilities
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaInfoAvailabilityDbModel 
	extends MetaInfoAvailabilityDbModelFactoryLike[PersonaInfoAvailabilityDbModel, PersonaInfoAvailability, PersonaInfoAvailabilityData] 
		with PersonaInfoAvailabilityFactory[PersonaInfoAvailabilityDbModel] with MetaInfoAvailabilityDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with persona ids
	  */
	lazy val personaId = property("personaId")
	
	/**
	  * Database property used for interacting with field ids
	  */
	override lazy val fieldId = property("fieldId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	override lazy val created = property("created")
	
	
	// IMPLEMENTED	--------------------
	
	override def contextId = personaId
	
	override def table = LlamaChatTables.personaInfoAvailability
	
	override def apply(data: PersonaInfoAvailabilityData) = 
		apply(None, Some(data.personaId), Some(data.fieldId), Some(data.created))
	
	/**
	  * @param created Time when this information was made available
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param fieldId Id of the meta info -field made available
	  * @return A model containing only the specified field id
	  */
	override def withFieldId(fieldId: Int) = apply(fieldId = Some(fieldId))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param personaId Id of the persona to which the linked information is made available
	  * @return A model containing only the specified persona id
	  */
	override def withPersonaId(personaId: Int) = apply(personaId = Some(personaId))
	
	override protected def complete(id: Value, data: PersonaInfoAvailabilityData) = 
		PersonaInfoAvailability(id.getInt, data)
}

/**
  * Used for interacting with PersonaInfoAvailabilities in the database
  * @param id persona info availability database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaInfoAvailabilityDbModel(id: Option[Int] = None, personaId: Option[Int] = None, 
	fieldId: Option[Int] = None, created: Option[Instant] = None) 
	extends MetaInfoAvailabilityDbModel with MetaInfoAvailabilityDbModelLike[PersonaInfoAvailabilityDbModel] 
		with PersonaInfoAvailabilityFactory[PersonaInfoAvailabilityDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def contextId = personaId
	
	override def dbProps = PersonaInfoAvailabilityDbModel
	
	override def table = PersonaInfoAvailabilityDbModel.table
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param fieldId field id to assign to the new model (default = currently assigned value)
	  * @param contextId context id to assign to the new model (default = currently assigned value)
	  * @param created created to assign to the new model (default = currently assigned value)
	  */
	override def copyMetaInfoAvailability(id: Option[Int] = id, fieldId: Option[Int] = fieldId, 
		contextId: Option[Int] = contextId, created: Option[Instant] = created) = 
		copy(id = id, fieldId = fieldId, personaId = contextId, created = created)
	
	/**
	  * @param personaId Id of the persona to which the linked information is made available
	  * @return A new copy of this model with the specified persona id
	  */
	override def withPersonaId(personaId: Int) = copy(personaId = Some(personaId))
}

