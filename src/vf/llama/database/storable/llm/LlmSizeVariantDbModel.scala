package vf.llama.database.storable.llm

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.llm.LlmSizeVariantFactory
import vf.llama.model.partial.llm.LlmSizeVariantData
import vf.llama.model.stored.llm.LlmSizeVariant

import java.time.Instant

/**
  * Used for constructing LlmSizeVariantDbModel instances and for inserting llm size variants to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object LlmSizeVariantDbModel 
	extends StorableFactory[LlmSizeVariantDbModel, LlmSizeVariant, LlmSizeVariantData] 
		with FromIdFactory[Int, LlmSizeVariantDbModel] with HasIdProperty 
		with LlmSizeVariantFactory[LlmSizeVariantDbModel] with NullDeprecatable[LlmSizeVariantDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with llm ids
	  */
	lazy val llmId = property("llmId")
	
	/**
	  * Database property used for interacting with suffixes
	  */
	lazy val suffix = property("suffix")
	
	/**
	  * Database property used for interacting with sizes
	  */
	lazy val size = property("size")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with removed afters
	  */
	lazy val removedAfter = property("removedAfter")
	
	override val deprecationAttName = "removedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.llmSizeVariant
	
	override def apply(data: LlmSizeVariantData) = 
		apply(None, Some(data.llmId), data.suffix, data.size, Some(data.created), data.removedAfter)
	
	/**
	  * @param created Time when this llm size variant was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withRemovedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param llmId Id of the LLM this is a variant of
	  * @return A model containing only the specified llm id
	  */
	override def withLlmId(llmId: Int) = apply(llmId = Some(llmId))
	
	/**
	  * @param removedAfter Time when this specific variant was removed from this application
	  * @return A model containing only the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = apply(removedAfter = Some(removedAfter))
	
	/**
	  * @param size Size of this LLM variant in billions of parameters. None if unknown.
	  * @return A model containing only the specified size
	  */
	override def withSize(size: Int) = apply(size = Some(size))
	
	/**
	  * @param suffix Suffix added to the end of the LLM's name when targeting this specific variant
	  * @return A model containing only the specified suffix
	  */
	override def withSuffix(suffix: String) = apply(suffix = suffix)
	
	override protected def complete(id: Value, data: LlmSizeVariantData) = LlmSizeVariant(id.getInt, data)
}

/**
  * Used for interacting with LlmSizeVariants in the database
  * @param id llm size variant database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class LlmSizeVariantDbModel(id: Option[Int] = None, llmId: Option[Int] = None, suffix: String = "", 
	size: Option[Int] = None, created: Option[Instant] = None, removedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, LlmSizeVariantDbModel] 
		with LlmSizeVariantFactory[LlmSizeVariantDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = LlmSizeVariantDbModel.table
	
	override def valueProperties = 
		Vector(LlmSizeVariantDbModel.id.name -> id, LlmSizeVariantDbModel.llmId.name -> llmId, 
			LlmSizeVariantDbModel.suffix.name -> suffix, LlmSizeVariantDbModel.size.name -> size, 
			LlmSizeVariantDbModel.created.name -> created, 
			LlmSizeVariantDbModel.removedAfter.name -> removedAfter)
	
	/**
	  * @param created Time when this llm size variant was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param llmId Id of the LLM this is a variant of
	  * @return A new copy of this model with the specified llm id
	  */
	override def withLlmId(llmId: Int) = copy(llmId = Some(llmId))
	
	/**
	  * @param removedAfter Time when this specific variant was removed from this application
	  * @return A new copy of this model with the specified removed after
	  */
	override def withRemovedAfter(removedAfter: Instant) = copy(removedAfter = Some(removedAfter))
	
	/**
	  * @param size Size of this LLM variant in billions of parameters. None if unknown.
	  * @return A new copy of this model with the specified size
	  */
	override def withSize(size: Int) = copy(size = Some(size))
	
	/**
	  * @param suffix Suffix added to the end of the LLM's name when targeting this specific variant
	  * @return A new copy of this model with the specified suffix
	  */
	override def withSuffix(suffix: String) = copy(suffix = suffix)
}

