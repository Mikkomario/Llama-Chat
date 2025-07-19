package vf.llama.model.partial.persona

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration, Value}
import utopia.flow.generic.model.mutable.DataType.AnyType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import vf.llama.model.factory.persona.PersonaParameterFactory

object PersonaParameterData extends FromModelFactoryWithSchema[PersonaParameterData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("settingsId", IntType, Single("settings_id")), 
			PropertyDeclaration("key", StringType), PropertyDeclaration("value", AnyType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaParameterData(valid("settingsId").getInt, valid("key").getString, valid("value"))
}

/**
  * Represents an individual parameter / option assignment which is tied to a persona (version)
  * @param settingsId Id of the persona settings this parameter assignment is part of
  * @param key Name / key of the adjusted parameter
  * @param value Value assigned to this parameter
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaParameterData(settingsId: Int, key: String, value: Value = Value.empty) 
	extends PersonaParameterFactory[PersonaParameterData] with ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("settingsId" -> settingsId, "key" -> key, "value" -> value))
	
	override def withKey(key: String) = copy(key = key)
	
	override def withSettingsId(settingsId: Int) = copy(settingsId = settingsId)
	
	override def withValue(value: Value) = copy(value = value)
}

