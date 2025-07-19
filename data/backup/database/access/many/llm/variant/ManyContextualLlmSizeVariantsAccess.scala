package vf.llama.database.access.many.llm.variant

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.ContextualLlmSizeVariantDbFactory
import vf.llama.database.storable.llm.LlmDbModel
import vf.llama.model.combined.llm.ContextualLlmSizeVariant

import java.time.Instant

object ManyContextualLlmSizeVariantsAccess extends ViewFactory[ManyContextualLlmSizeVariantsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyContextualLlmSizeVariantsAccess = 
		_ManyContextualLlmSizeVariantsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyContextualLlmSizeVariantsAccess(override val accessCondition: Option[Condition]) 
		extends ManyContextualLlmSizeVariantsAccess
}

/**
  * A common trait for access points that return multiple contextual llm size variants at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyContextualLlmSizeVariantsAccess 
	extends ManyLlmSizeVariantsAccessLike[ContextualLlmSizeVariant, ManyContextualLlmSizeVariantsAccess] 
		with ManyRowModelAccess[ContextualLlmSizeVariant]
{
	// COMPUTED	--------------------
	
	/**
	  * names of the accessible llms
	  */
	def llmNames(implicit connection: Connection) = pullColumn(llmModel.name.column).flatMap { _.string }
	
	/**
	  * aliases of the accessible llms
	  */
	def llmAliases(implicit connection: Connection) = pullColumn(llmModel.alias.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible llms
	  */
	def llmCreationTimes(implicit connection: Connection) = 
		pullColumn(llmModel.created.column).map { v => v.getInstant }
	
	/**
	  * removed afters of the accessible llms
	  */
	def llmRemovedAfters(implicit connection: Connection) = 
		pullColumn(llmModel.removedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Model (factory) used for interacting the llms associated with this contextual llm size variant
	  */
	protected def llmModel = LlmDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ContextualLlmSizeVariantDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyContextualLlmSizeVariantsAccess = 
		ManyContextualLlmSizeVariantsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the aliases of the targeted llms
	  * @param newAlias A new alias to assign
	  * @return Whether any llm was affected
	  */
	def llmAliases_=(newAlias: String)(implicit connection: Connection) = 
		putColumn(llmModel.alias.column, newAlias)
	
	/**
	  * Updates the removed afters of the targeted llms
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any llm was affected
	  */
	def llmRemovedAfters_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(llmModel.removedAfter.column, newRemovedAfter)
}

