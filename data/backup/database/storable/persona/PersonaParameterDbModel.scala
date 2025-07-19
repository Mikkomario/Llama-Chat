package vf.llama.database.storable.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.persona.PersonaParameterFactory
import vf.llama.model.partial.persona.PersonaParameterData
import vf.llama.model.stored.persona.PersonaParameter

/**
  * 
	Used for constructing PersonaParameterDbModel instances and for inserting persona parameters to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaParameterDbModel 
	extends StorableFactory[PersonaParameterDbModel, PersonaParameter, PersonaParameterData] 
		with FromIdFactory[Int, PersonaParameterDbModel] with HasIdProperty 
		with PersonaParameterFactory[PersonaParameterDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with settings ids
	  */
	lazy val settingsId = property("settingsId")
	
	/**
	  * Database property used for interacting with keys
	  */
	lazy val key = property("key")
	
	/**
	  * Database property used for interacting with values
	  */
	lazy val value = property("value")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.personaParameter
	
	override def apply(data: PersonaParameterData) = apply(None, Some(data.settingsId), data.key, data.value)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param key Name / key of the adjusted parameter
	  * @return A model containing only the specified key
	  */
	override def withKey(key: String) = apply(key = key)
	
	/**
	  * @param settingsId Id of the persona settings this parameter assignment is part of
	  * @return A model containing only the specified settings id
	  */
	override def withSettingsId(settingsId: Int) = apply(settingsId = Some(settingsId))
	
	/**
	  * @param value Value assigned to this parameter
	  * @return A model containing only the specified value
	  */
	override def withValue(value: Value) = apply(value = value)
	
	override protected def complete(id: Value, data: PersonaParameterData) = PersonaParameter(id.getInt, data)
}

/**
  * Used for interacting with PersonaParameters in the database
  * @param id persona parameter database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaParameterDbModel(id: Option[Int] = None, settingsId: Option[Int] = None, key: String = "", 
	value: Value = Value.empty) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, PersonaParameterDbModel] 
		with PersonaParameterFactory[PersonaParameterDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = PersonaParameterDbModel.table
	
	override def valueProperties = 
		Vector(PersonaParameterDbModel.id.name -> id, PersonaParameterDbModel.settingsId.name -> settingsId, 
			PersonaParameterDbModel.key.name -> key, 
			PersonaParameterDbModel.value.name -> value.mapIfNotEmpty { _.toJson })
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param key Name / key of the adjusted parameter
	  * @return A new copy of this model with the specified key
	  */
	override def withKey(key: String) = copy(key = key)
	
	/**
	  * @param settingsId Id of the persona settings this parameter assignment is part of
	  * @return A new copy of this model with the specified settings id
	  */
	override def withSettingsId(settingsId: Int) = copy(settingsId = Some(settingsId))
	
	/**
	  * @param value Value assigned to this parameter
	  * @return A new copy of this model with the specified value
	  */
	override def withValue(value: Value) = copy(value = value)
}

