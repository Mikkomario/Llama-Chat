package vf.llama.model.partial.instruction

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.instruction.InstructionVersionFactory

import java.time.Instant

object InstructionVersionData extends FromModelFactoryWithSchema[InstructionVersionData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("name", StringType, isOptional = true), 
			PropertyDeclaration("enumValueId", IntType, Single("enum_value_id"), isOptional = true), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("deprecatedAfter", InstantType, Single("deprecated_after"), 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		InstructionVersionData(valid("name").getString, valid("enumValueId").int, 
			valid("created").getInstant, valid("deprecatedAfter").instant)
}

/**
  * Represents a specific instruction version. Different versions may have different configurations, syntax, 
	etc.
  * @param name Name of this instruction
  * @param enumValueId Id of the enumeration value selected by applying this instruction. None if
  *  not enumeration-based.
  * @param created Time when this instruction version was added to the database
  * @param deprecatedAfter Time when this version was replaced with a new one
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class InstructionVersionData(name: String = "", enumValueId: Option[Int] = None, created: Instant = Now, 
	deprecatedAfter: Option[Instant] = None) 
	extends InstructionVersionFactory[InstructionVersionData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this instruction version has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this instruction version is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("name" -> name, "enumValueId" -> enumValueId, "created" -> created, 
			"deprecatedAfter" -> deprecatedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withEnumValueId(enumValueId: Int) = copy(enumValueId = Some(enumValueId))
	
	override def withName(name: String) = copy(name = name)
}

