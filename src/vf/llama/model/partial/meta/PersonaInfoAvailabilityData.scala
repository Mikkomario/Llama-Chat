package vf.llama.model.partial.meta

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.time.Now
import vf.llama.model.factory.meta.PersonaInfoAvailabilityFactory

import java.time.Instant

object PersonaInfoAvailabilityData extends FromModelFactoryWithSchema[PersonaInfoAvailabilityData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("personaId", IntType, Vector("contextId", "context_id", 
			"persona_id")), PropertyDeclaration("fieldId", IntType, Single("field_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaInfoAvailabilityData(valid("personaId").getInt, valid("fieldId").getInt, 
			valid("created").getInstant)
}

/**
  * A link that makes a meta info field available to a persona
  * @param personaId Id of the persona to which the linked information is made available
  * @param fieldId Id of the meta info -field made available
  * @param created Time when this information was made available
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaInfoAvailabilityData(personaId: Int, fieldId: Int, created: Instant = Now) 
	extends PersonaInfoAvailabilityFactory[PersonaInfoAvailabilityData] with MetaInfoAvailabilityData 
		with MetaInfoAvailabilityDataLike[PersonaInfoAvailabilityData]
{
	// IMPLEMENTED	--------------------
	
	override def contextId = personaId
	
	override def copyMetaInfoAvailability(fieldId: Int, contextId: Int, created: Instant) = 
		copy(personaId = contextId, fieldId = fieldId, created = created)
	
	override def withPersonaId(personaId: Int) = copy(personaId = personaId)
}

