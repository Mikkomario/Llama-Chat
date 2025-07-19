package vf.llama.model.partial.enums

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.enums.EnumValueFactory

import java.time.Instant

object EnumValueData extends FromModelFactoryWithSchema[EnumValueData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("enumId", IntType, Single("enum_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("archivedAfter", InstantType, Single("archived_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		EnumValueData(valid("enumId").getInt, valid("created").getInstant, valid("archivedAfter").instant)
}

/**
  * Represents an individual enumeration value
  * @param enumId Id of the enumeration where this value appears
  * @param created Time when this enum value was added to the database
  * @param archivedAfter Time when this enumeration value was archived
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumValueData(enumId: Int, created: Instant = Now, archivedAfter: Option[Instant] = None) 
	extends EnumValueFactory[EnumValueData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this enum value has already been deprecated
	  */
	def isDeprecated = archivedAfter.isDefined
	
	/**
	  * Whether this enum value is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("enumId" -> enumId, "created" -> created, "archivedAfter" -> archivedAfter))
	
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withEnumId(enumId: Int) = copy(enumId = enumId)
}

