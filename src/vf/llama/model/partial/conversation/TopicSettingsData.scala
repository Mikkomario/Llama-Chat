package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.conversation.TopicSettingsFactory

import java.time.Instant

object TopicSettingsData extends FromModelFactoryWithSchema[TopicSettingsData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("topicId", IntType, Single("topic_id")), 
			PropertyDeclaration("personaId", IntType, Single("persona_id")), PropertyDeclaration("name", 
			StringType, isOptional = true), PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("deprecatedAfter", InstantType, Single("deprecated_after"), 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		TopicSettingsData(valid("topicId").getInt, valid("personaId").getInt, valid("name").getString, 
			valid("created").getInstant, valid("deprecatedAfter").instant)
}

/**
  * Specifies the name of a topic and the persona with whom that topic is discussed
  * @param topicId Id of the topic these settings describe
  * @param personaId Id of the persona with whom this topic is discussed
  * @param name Name assigned to this topic
  * @param created Time when this topic settings was added to the database
  * @param deprecatedAfter Time when these settings were replaced with another version
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicSettingsData(topicId: Int, personaId: Int, name: String = "", created: Instant = Now, 
	deprecatedAfter: Option[Instant] = None) 
	extends TopicSettingsFactory[TopicSettingsData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this topic settings has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this topic settings is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("topicId" -> topicId, "personaId" -> personaId, "name" -> name, "created" -> created, 
			"deprecatedAfter" -> deprecatedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withName(name: String) = copy(name = name)
	
	override def withPersonaId(personaId: Int) = copy(personaId = personaId)
	
	override def withTopicId(topicId: Int) = copy(topicId = topicId)
}

