package vf.llama.model.partial.meta

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.meta.CustomMetaInfoFactory

import java.time.Instant

object CustomMetaInfoData extends FromModelFactoryWithSchema[CustomMetaInfoData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("name", StringType), PropertyDeclaration("value", 
			StringType), PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("deprecatedAfter", InstantType, Single("deprecated_after"), 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		CustomMetaInfoData(valid("name").getString, valid("value").getString, valid("created").getInstant, 
			valid("deprecatedAfter").instant)
}

/**
  * Represents a user-defined meta info field definition
  * @param name Name of this field
  * @param value Value assigned to this field
  * @param created Time when this custom meta info was added to the database
  * @param deprecatedAfter Time when this version was replaced with another
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class CustomMetaInfoData(name: String, value: String, created: Instant = Now, 
	deprecatedAfter: Option[Instant] = None) 
	extends CustomMetaInfoFactory[CustomMetaInfoData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this custom meta info has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this custom meta info is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("name" -> name, "value" -> value, "created" -> created, 
			"deprecatedAfter" -> deprecatedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withName(name: String) = copy(name = name)
	
	override def withValue(value: String) = copy(value = value)
}

