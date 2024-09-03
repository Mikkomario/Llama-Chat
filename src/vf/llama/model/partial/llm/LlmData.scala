package vf.llama.model.partial.llm

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.llm.LlmFactory

import java.time.Instant

object LlmData extends FromModelFactoryWithSchema[LlmData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("name", StringType), PropertyDeclaration("alias", 
			StringType, isOptional = true), PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("removedAfter", InstantType, Single("removed_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		LlmData(valid("name").getString, valid("alias").getString, valid("created").getInstant, 
			valid("removedAfter").instant)
}

/**
  * Represents a large language model
  * @param name Name of this LLM in Ollama
  * @param alias Alias given to this LLM
  * @param created Time when this llm was added to the database
  * @param removedAfter Time when this LLM was removed from this application
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class LlmData(name: String, alias: String = "", created: Instant = Now, 
	removedAfter: Option[Instant] = None) 
	extends LlmFactory[LlmData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this llm has already been deprecated
	  */
	def isDeprecated = removedAfter.isDefined
	
	/**
	  * Whether this llm is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("name" -> name, "alias" -> alias, "created" -> created, "removedAfter" -> removedAfter))
	
	override def withAlias(alias: String) = copy(alias = alias)
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withName(name: String) = copy(name = name)
	
	override def withRemovedAfter(removedAfter: Instant) = copy(removedAfter = Some(removedAfter))
}

