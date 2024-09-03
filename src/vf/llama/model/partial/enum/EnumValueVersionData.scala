package vf.llama.model.partial.`enum`

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.enum.EnumValueVersionFactory

import java.time.Instant

object EnumValueVersionData extends FromModelFactoryWithSchema[EnumValueVersionData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("enumValueId", IntType, Single("enum_value_id")), 
			PropertyDeclaration("name", StringType), PropertyDeclaration("colorId", IntType, 
			Single("color_id")), PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("deprecatedAfter", InstantType, Single("deprecated_after"), 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		EnumValueVersionData(valid("enumValueId").getInt, valid("name").getString, valid("colorId").getInt, 
			valid("created").getInstant, valid("deprecatedAfter").instant)
}

/**
  * Represents a specific version of an enumeration value
  * @param enumValueId Id of the enumeration value which this version describes
  * @param name Name of this enumeration value
  * @param colorId Id of the color used for this enumeration value
  * @param created Time when this enum value version was added to the database
  * @param deprecatedAfter Time when this version was replaced with a newer one
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumValueVersionData(enumValueId: Int, name: String, colorId: Int, created: Instant = Now, 
	deprecatedAfter: Option[Instant] = None) 
	extends EnumValueVersionFactory[EnumValueVersionData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this enum value version has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this enum value version is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("enumValueId" -> enumValueId, "name" -> name, "colorId" -> colorId, 
			"created" -> created, "deprecatedAfter" -> deprecatedAfter))
	
	override def withColorId(colorId: Int) = copy(colorId = colorId)
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withEnumValueId(enumValueId: Int) = copy(enumValueId = enumValueId)
	
	override def withName(name: String) = copy(name = name)
}

