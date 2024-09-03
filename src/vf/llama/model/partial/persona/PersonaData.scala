package vf.llama.model.partial.persona

import utopia.flow.collection.immutable.{Pair, Single}
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.persona.PersonaFactory

import java.time.Instant

object PersonaData extends FromModelFactoryWithSchema[PersonaData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Pair(PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("deletedAfter", InstantType, Single("deleted_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaData(valid("created").getInstant, valid("deletedAfter").instant)
}

/**
  * Represents a personalized large language model
  * @param created Time when this persona was added to the database
  * @param deletedAfter Time when this persona was deleted
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaData(created: Instant = Now, deletedAfter: Option[Instant] = None) 
	extends PersonaFactory[PersonaData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this persona has already been deprecated
	  */
	def isDeprecated = deletedAfter.isDefined
	
	/**
	  * Whether this persona is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Pair("created" -> created, "deletedAfter" -> deletedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withDeletedAfter(deletedAfter: Instant) = copy(deletedAfter = Some(deletedAfter))
}

