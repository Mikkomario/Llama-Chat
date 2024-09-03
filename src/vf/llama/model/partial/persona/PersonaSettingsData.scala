package vf.llama.model.partial.persona

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.persona.PersonaSettingsFactory

import java.time.Instant

object PersonaSettingsData extends FromModelFactoryWithSchema[PersonaSettingsData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("personaId", IntType, Single("persona_id")), 
			PropertyDeclaration("llmVariantId", IntType, Single("llm_variant_id")), 
			PropertyDeclaration("name", StringType, isOptional = true), PropertyDeclaration("created", 
			InstantType, isOptional = true), PropertyDeclaration("deprecatedAfter", InstantType, 
			Single("deprecated_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaSettingsData(valid("personaId").getInt, valid("llmVariantId").getInt, valid("name").getString, 
			valid("created").getInstant, valid("deprecatedAfter").instant)
}

/**
  * Records general settings (version) for a Persona
  * @param personaId Id of the persona which these settings describe
  * @param llmVariantId Id of the LLM variant this persona wraps
  * @param name Name assigned for this persona
  * @param created Time when this persona settings was added to the database
  * @param deprecatedAfter Time when these settings were replaced with a new version
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaSettingsData(personaId: Int, llmVariantId: Int, name: String = "", created: Instant = Now, 
	deprecatedAfter: Option[Instant] = None) 
	extends PersonaSettingsFactory[PersonaSettingsData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this persona settings has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this persona settings is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("personaId" -> personaId, "llmVariantId" -> llmVariantId, "name" -> name, 
			"created" -> created, "deprecatedAfter" -> deprecatedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withLlmVariantId(llmVariantId: Int) = copy(llmVariantId = llmVariantId)
	
	override def withName(name: String) = copy(name = name)
	
	override def withPersonaId(personaId: Int) = copy(personaId = personaId)
}

