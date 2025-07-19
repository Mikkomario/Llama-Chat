package vf.llama.database.storable.conversation

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.conversation.TopicSettingsFactory
import vf.llama.model.partial.conversation.TopicSettingsData
import vf.llama.model.stored.conversation.TopicSettings

import java.time.Instant

/**
  * Used for constructing TopicSettingsDbModel instances and for inserting topic settings to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object TopicSettingsDbModel 
	extends StorableFactory[TopicSettingsDbModel, TopicSettings, TopicSettingsData] 
		with FromIdFactory[Int, TopicSettingsDbModel] with HasIdProperty 
		with TopicSettingsFactory[TopicSettingsDbModel] with DeprecatableAfter[TopicSettingsDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with topic ids
	  */
	lazy val topicId = property("topicId")
	
	/**
	  * Database property used for interacting with persona ids
	  */
	lazy val personaId = property("personaId")
	
	/**
	  * Database property used for interacting with names
	  */
	lazy val name = property("name")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with deprecation times
	  */
	lazy val deprecatedAfter = property("deprecatedAfter")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.topicSettings
	
	override def apply(data: TopicSettingsData) = 
		apply(None, Some(data.topicId), Some(data.personaId), data.name, Some(data.created), 
			data.deprecatedAfter)
	
	/**
	  * @param created Time when this topic settings was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when these settings were replaced with another version
	  * @return A model containing only the specified deprecated after
	  */
	override
		 def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param name Name assigned to this topic
	  * @return A model containing only the specified name
	  */
	override def withName(name: String) = apply(name = name)
	
	/**
	  * @param personaId Id of the persona with whom this topic is discussed
	  * @return A model containing only the specified persona id
	  */
	override def withPersonaId(personaId: Int) = apply(personaId = Some(personaId))
	
	/**
	  * @param topicId Id of the topic these settings describe
	  * @return A model containing only the specified topic id
	  */
	override def withTopicId(topicId: Int) = apply(topicId = Some(topicId))
	
	override protected def complete(id: Value, data: TopicSettingsData) = TopicSettings(id.getInt, data)
}

/**
  * Used for interacting with TopicSettings in the database
  * @param id topic settings database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicSettingsDbModel(id: Option[Int] = None, topicId: Option[Int] = None, 
	personaId: Option[Int] = None, name: String = "", created: Option[Instant] = None, 
	deprecatedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, TopicSettingsDbModel] 
		with TopicSettingsFactory[TopicSettingsDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = TopicSettingsDbModel.table
	
	override def valueProperties = 
		Vector(TopicSettingsDbModel.id.name -> id, TopicSettingsDbModel.topicId.name -> topicId, 
			TopicSettingsDbModel.personaId.name -> personaId, TopicSettingsDbModel.name.name -> name, 
			TopicSettingsDbModel.created.name -> created, 
			TopicSettingsDbModel.deprecatedAfter.name -> deprecatedAfter)
	
	/**
	  * @param created Time when this topic settings was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when these settings were replaced with another version
	  * @return A new copy of this model with the specified deprecated after
	  */
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param name Name assigned to this topic
	  * @return A new copy of this model with the specified name
	  */
	override def withName(name: String) = copy(name = name)
	
	/**
	  * @param personaId Id of the persona with whom this topic is discussed
	  * @return A new copy of this model with the specified persona id
	  */
	override def withPersonaId(personaId: Int) = copy(personaId = Some(personaId))
	
	/**
	  * @param topicId Id of the topic these settings describe
	  * @return A new copy of this model with the specified topic id
	  */
	override def withTopicId(topicId: Int) = copy(topicId = Some(topicId))
}

