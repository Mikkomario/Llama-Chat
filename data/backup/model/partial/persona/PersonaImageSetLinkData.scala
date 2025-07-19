package vf.llama.model.partial.persona

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.persona.PersonaImageSetLinkFactory

import java.time.Instant

object PersonaImageSetLinkData extends FromModelFactoryWithSchema[PersonaImageSetLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("personaId", IntType, Single("persona_id")), 
			PropertyDeclaration("imageSetId", IntType, Single("image_set_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("removedAfter", InstantType, Single("removed_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaImageSetLinkData(valid("personaId").getInt, valid("imageSetId").getInt, 
			valid("created").getInstant, valid("removedAfter").instant)
}

/**
  * Links a Persona with an image set used for that Persona
  * @param personaId Id of the persona for which the linked images are used
  * @param imageSetId Id of the image set used for the linked persona
  * @param created Time when this persona image set link was added to the database
  * @param removedAfter Time when this link was removed
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaImageSetLinkData(personaId: Int, imageSetId: Int, created: Instant = Now, 
	removedAfter: Option[Instant] = None) 
	extends PersonaImageSetLinkFactory[PersonaImageSetLinkData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this persona image set link has already been deprecated
	  */
	def isDeprecated = removedAfter.isDefined
	
	/**
	  * Whether this persona image set link is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("personaId" -> personaId, "imageSetId" -> imageSetId, "created" -> created, 
			"removedAfter" -> removedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withImageSetId(imageSetId: Int) = copy(imageSetId = imageSetId)
	
	override def withPersonaId(personaId: Int) = copy(personaId = personaId)
	
	override def withRemovedAfter(removedAfter: Instant) = copy(removedAfter = Some(removedAfter))
}

