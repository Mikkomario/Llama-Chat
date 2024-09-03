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
import vf.llama.model.factory.conversation.ThreadFactory

import java.time.Instant

object ThreadData extends FromModelFactoryWithSchema[ThreadData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("topicId", IntType, Single("topic_id")), 
			PropertyDeclaration("name", StringType, isOptional = true), PropertyDeclaration("created", 
			InstantType, isOptional = true), PropertyDeclaration("archivedAfter", InstantType, 
			Single("archived_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		ThreadData(valid("topicId").getInt, valid("name").getString, valid("created").getInstant, 
			valid("archivedAfter").instant)
}

/**
  * Represents a single named sequence of conversations within a specific topic
  * @param topicId Id of the general topic of this thread
  * @param name The current name of this thread
  * @param created Time when this thread was added to the database
  * @param archivedAfter Time when this thread was archived
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ThreadData(topicId: Int, name: String = "", created: Instant = Now, 
	archivedAfter: Option[Instant] = None) 
	extends ThreadFactory[ThreadData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this thread has already been deprecated
	  */
	def isDeprecated = archivedAfter.isDefined
	
	/**
	  * Whether this thread is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("topicId" -> topicId, "name" -> name, "created" -> created, 
			"archivedAfter" -> archivedAfter))
	
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withName(name: String) = copy(name = name)
	
	override def withTopicId(topicId: Int) = copy(topicId = topicId)
}

