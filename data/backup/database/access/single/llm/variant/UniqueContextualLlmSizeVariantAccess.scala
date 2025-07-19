package vf.llama.database.access.single.llm.variant

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.ContextualLlmSizeVariantDbFactory
import vf.llama.database.storable.llm.LlmDbModel
import vf.llama.model.combined.llm.ContextualLlmSizeVariant

import java.time.Instant

object UniqueContextualLlmSizeVariantAccess extends ViewFactory[UniqueContextualLlmSizeVariantAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueContextualLlmSizeVariantAccess = 
		_UniqueContextualLlmSizeVariantAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueContextualLlmSizeVariantAccess(override val accessCondition: Option[Condition]) 
		extends UniqueContextualLlmSizeVariantAccess
}

/**
  * A common trait for access points that return distinct contextual llm size variants
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueContextualLlmSizeVariantAccess 
	extends UniqueLlmSizeVariantAccessLike[ContextualLlmSizeVariant, UniqueContextualLlmSizeVariantAccess] 
		with SingleRowModelAccess[ContextualLlmSizeVariant] 
		with NullDeprecatableView[UniqueContextualLlmSizeVariantAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Name of this LLM in Ollama. 
	  * None if no llm (or value) was found.
	  */
	def llmName(implicit connection: Connection) = pullColumn(llmModel.name.column).getString
	
	/**
	  * Alias given to this LLM. 
	  * None if no llm (or value) was found.
	  */
	def llmAlias(implicit connection: Connection) = pullColumn(llmModel.alias.column).getString
	
	/**
	  * Time when this llm was added to the database. 
	  * None if no llm (or value) was found.
	  */
	def llmCreated(implicit connection: Connection) = pullColumn(llmModel.created.column).instant
	
	/**
	  * Time when this LLM was removed from this application. 
	  * None if no llm (or value) was found.
	  */
	def llmRemovedAfter(implicit connection: Connection) = pullColumn(llmModel.removedAfter.column).instant
	
	/**
	  * A database model (factory) used for interacting with the linked llm
	  */
	protected def llmModel = LlmDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ContextualLlmSizeVariantDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueContextualLlmSizeVariantAccess = 
		UniqueContextualLlmSizeVariantAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the aliases of the targeted llms
	  * @param newAlias A new alias to assign
	  * @return Whether any llm was affected
	  */
	def llmAlias_=(newAlias: String)(implicit connection: Connection) = putColumn(llmModel.alias.column, 
		newAlias)
	
	/**
	  * Updates the removed afters of the targeted llms
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any llm was affected
	  */
	def llmRemovedAfter_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(llmModel.removedAfter.column, newRemovedAfter)
}

