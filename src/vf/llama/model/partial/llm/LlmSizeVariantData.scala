package vf.llama.model.partial.llm

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.llm.LlmSizeVariantFactory

import java.time.Instant

object LlmSizeVariantData extends FromModelFactoryWithSchema[LlmSizeVariantData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("llmId", IntType, Single("llm_id")), 
			PropertyDeclaration("suffix", StringType, isOptional = true), PropertyDeclaration("size", 
			IntType, isOptional = true), PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("removedAfter", InstantType, Single("removed_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		LlmSizeVariantData(valid("llmId").getInt, valid("suffix").getString, valid("size").int, 
			valid("created").getInstant, valid("removedAfter").instant)
}

/**
  * Represents a specific version (size) of a large language model
  * @param llmId Id of the LLM this is a variant of
  * @param suffix Suffix added to the end of the LLM's name when targeting this specific variant
  * @param size Size of this LLM variant in billions of parameters. None if unknown.
  * @param created Time when this llm size variant was added to the database
  * @param removedAfter Time when this specific variant was removed from this application
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class LlmSizeVariantData(llmId: Int, suffix: String = "", size: Option[Int] = None, 
	created: Instant = Now, removedAfter: Option[Instant] = None) 
	extends LlmSizeVariantFactory[LlmSizeVariantData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this llm size variant has already been deprecated
	  */
	def isDeprecated = removedAfter.isDefined
	
	/**
	  * Whether this llm size variant is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("llmId" -> llmId, "suffix" -> suffix, "size" -> size, "created" -> created, 
			"removedAfter" -> removedAfter))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withLlmId(llmId: Int) = copy(llmId = llmId)
	
	override def withRemovedAfter(removedAfter: Instant) = copy(removedAfter = Some(removedAfter))
	
	override def withSize(size: Int) = copy(size = Some(size))
	
	override def withSuffix(suffix: String) = copy(suffix = suffix)
}

