package vf.llama.database.storable.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.persona.PersonaSettingsFactory
import vf.llama.model.partial.persona.PersonaSettingsData
import vf.llama.model.stored.persona.PersonaSettings

import java.time.Instant

/**
  * Used for constructing PersonaSettingsDbModel instances and for inserting persona settings to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaSettingsDbModel 
	extends StorableFactory[PersonaSettingsDbModel, PersonaSettings, PersonaSettingsData] 
		with FromIdFactory[Int, PersonaSettingsDbModel] with HasIdProperty 
		with PersonaSettingsFactory[PersonaSettingsDbModel] with DeprecatableAfter[PersonaSettingsDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with persona ids
	  */
	lazy val personaId = property("personaId")
	
	/**
	  * Database property used for interacting with llm variant ids
	  */
	lazy val llmVariantId = property("llmVariantId")
	
	/**
	  * Database property used for interacting with names
	  */
	lazy val name = property("name")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with deprecation times
	  */
	lazy val deprecatedAfter = property("deprecatedAfter")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.personaSettings
	
	override def apply(data: PersonaSettingsData) = 
		apply(None, Some(data.personaId), Some(data.llmVariantId), data.name, Some(data.created), 
			data.deprecatedAfter)
	
	/**
	  * @param created Time when this persona settings was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when these settings were replaced with a new version
	  * @return A model containing only the specified deprecated after
	  */
	override
		 def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param llmVariantId Id of the LLM variant this persona wraps
	  * @return A model containing only the specified llm variant id
	  */
	override def withLlmVariantId(llmVariantId: Int) = apply(llmVariantId = Some(llmVariantId))
	
	/**
	  * @param name Name assigned for this persona
	  * @return A model containing only the specified name
	  */
	override def withName(name: String) = apply(name = name)
	
	/**
	  * @param personaId Id of the persona which these settings describe
	  * @return A model containing only the specified persona id
	  */
	override def withPersonaId(personaId: Int) = apply(personaId = Some(personaId))
	
	override protected def complete(id: Value, data: PersonaSettingsData) = PersonaSettings(id.getInt, data)
}

/**
  * Used for interacting with PersonaSettings in the database
  * @param id persona settings database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaSettingsDbModel(id: Option[Int] = None, personaId: Option[Int] = None, 
	llmVariantId: Option[Int] = None, name: String = "", created: Option[Instant] = None, 
	deprecatedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, PersonaSettingsDbModel] 
		with PersonaSettingsFactory[PersonaSettingsDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = PersonaSettingsDbModel.table
	
	override def valueProperties = 
		Vector(PersonaSettingsDbModel.id.name -> id, PersonaSettingsDbModel.personaId.name -> personaId, 
			PersonaSettingsDbModel.llmVariantId.name -> llmVariantId, 
			PersonaSettingsDbModel.name.name -> name, PersonaSettingsDbModel.created.name -> created, 
			PersonaSettingsDbModel.deprecatedAfter.name -> deprecatedAfter)
	
	/**
	  * @param created Time when this persona settings was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when these settings were replaced with a new version
	  * @return A new copy of this model with the specified deprecated after
	  */
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param llmVariantId Id of the LLM variant this persona wraps
	  * @return A new copy of this model with the specified llm variant id
	  */
	override def withLlmVariantId(llmVariantId: Int) = copy(llmVariantId = Some(llmVariantId))
	
	/**
	  * @param name Name assigned for this persona
	  * @return A new copy of this model with the specified name
	  */
	override def withName(name: String) = copy(name = name)
	
	/**
	  * @param personaId Id of the persona which these settings describe
	  * @return A new copy of this model with the specified persona id
	  */
	override def withPersonaId(personaId: Int) = copy(personaId = Some(personaId))
}

