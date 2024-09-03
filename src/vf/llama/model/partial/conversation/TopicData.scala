package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.{Pair, Single}
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.conversation.TopicFactory

import java.time.Instant

object TopicData extends FromModelFactoryWithSchema[TopicData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Pair(PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("deprecatedAfter", InstantType, Single("deprecated_after"), 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		TopicData(valid("created").getInstant, valid("deprecatedAfter").instant)
}

/**
  * Represents a general conversation topic / theme, assigned to a single persona
  * @param created Time when this topic was added to the database
  * @param deprecatedAfter Time when this topic was archived
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicData(created: Instant = Now, deprecatedAfter: Option[Instant] = None) 
	extends TopicFactory[TopicData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this topic has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this topic is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Pair("created" -> created, "deprecatedAfter" -> deprecatedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
}

