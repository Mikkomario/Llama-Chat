package vf.llama.database.access.many.llm

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.LlmWithVariantsDbFactory
import vf.llama.database.storable.llm.LlmSizeVariantDbModel
import vf.llama.model.combined.llm.LlmWithVariants

import java.time.Instant

object ManyLlmWithVariantsesAccess extends ViewFactory[ManyLlmWithVariantsesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyLlmWithVariantsesAccess = 
		_ManyLlmWithVariantsesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyLlmWithVariantsesAccess(override val accessCondition: Option[Condition]) 
		extends ManyLlmWithVariantsesAccess
}

/**
  * A common trait for access points that return multiple llm with variantses at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyLlmWithVariantsesAccess extends ManyLlmsAccessLike[LlmWithVariants, ManyLlmWithVariantsesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * llm ids of the accessible llm size variants
	  */
	def variantLlmIds(implicit connection: Connection) = 
		pullColumn(variantModel.llmId.column).map { v => v.getInt }
	
	/**
	  * suffixes of the accessible llm size variants
	  */
	def variantSuffixes(implicit connection: Connection) = 
		pullColumn(variantModel.suffix.column).flatMap { _.string }
	
	/**
	  * sizes of the accessible llm size variants
	  */
	def variantSizes(implicit connection: Connection) = 
		pullColumn(variantModel.size.column).flatMap { v => v.int }
	
	/**
	  * creation times of the accessible llm size variants
	  */
	def variantCreationTimes(implicit connection: Connection) = 
		pullColumn(variantModel.created.column).map { v => v.getInstant }
	
	/**
	  * removed afters of the accessible llm size variants
	  */
	def variantRemovedAfters(implicit connection: Connection) = 
		pullColumn(variantModel.removedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Model (factory) used for interacting the llm size variants associated with this llm with variants
	  */
	protected def variantModel = LlmSizeVariantDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = LlmWithVariantsDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): ManyLlmWithVariantsesAccess = ManyLlmWithVariantsesAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the removed afters of the targeted llm size variants
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any llm size variant was affected
	  */
	def variantRemovedAfters_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(variantModel.removedAfter.column, newRemovedAfter)
}

