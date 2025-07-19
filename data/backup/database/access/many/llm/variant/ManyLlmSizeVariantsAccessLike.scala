package vf.llama.database.access.many.llm.variant

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.llm.LlmSizeVariantDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple llm size variants or similar instances at a time
  * @tparam A Type of read (llm size variants -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyLlmSizeVariantsAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * llm ids of the accessible llm size variants
	  */
	def llmIds(implicit connection: Connection) = pullColumn(model.llmId.column).map { v => v.getInt }
	
	/**
	  * suffixes of the accessible llm size variants
	  */
	def suffixes(implicit connection: Connection) = pullColumn(model.suffix.column).flatMap { _.string }
	
	/**
	  * sizes of the accessible llm size variants
	  */
	def sizes(implicit connection: Connection) = pullColumn(model.size.column).flatMap { v => v.int }
	
	/**
	  * creation times of the accessible llm size variants
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * removed afters of the accessible llm size variants
	  */
	def removedAfters(implicit connection: Connection) = 
		pullColumn(model.removedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible llm size variants
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = LlmSizeVariantDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * @param llmId llm id to target
	  * @return Copy of this access point that only includes llm size variants with the specified llm id
	  */
	def ofLlm(llmId: Int) = filter(model.llmId.column <=> llmId)
	
	/**
	  * @param llmIds Targeted llm ids
	  * 
		@return Copy of this access point that only includes llm size variants where llm id is within the specified
	  *  value set
	  */
	def ofLlms(llmIds: IterableOnce[Int]) = filter(model.llmId.column.in(IntSet.from(llmIds)))
	
	/**
	  * Updates the removed afters of the targeted llm size variants
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any llm size variant was affected
	  */
	def removedAfters_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
}

