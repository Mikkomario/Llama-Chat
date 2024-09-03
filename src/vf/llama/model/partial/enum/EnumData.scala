package vf.llama.model.partial.`enum`

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.enum.EnumFactory

import java.time.Instant

object EnumData extends FromModelFactoryWithSchema[EnumData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("name", StringType), PropertyDeclaration("created", 
			InstantType, isOptional = true), PropertyDeclaration("archivedAfter", InstantType, 
			Single("archived_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		EnumData(valid("name").getString, valid("created").getInstant, valid("archivedAfter").instant)
}

/**
  * Represents an enumeration
  * @param name Name of this enumeration (mutable)
  * @param created Time when this enum was added to the database
  * @param archivedAfter Time when this enumeration was archived
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumData(name: String, created: Instant = Now, archivedAfter: Option[Instant] = None) 
	extends EnumFactory[EnumData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this enum has already been deprecated
	  */
	def isDeprecated = archivedAfter.isDefined
	
	/**
	  * Whether this enum is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("name" -> name, "created" -> created, 
		"archivedAfter" -> archivedAfter))
	
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withName(name: String) = copy(name = name)
}

