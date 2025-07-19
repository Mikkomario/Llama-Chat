package vf.llama.database.access.single.llm.variant

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.llm.LlmSizeVariantDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual llm size variants or similar items at a time
  * @tparam A Type of read (llm size variants -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueLlmSizeVariantAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the LLM this is a variant of. 
	  * None if no llm size variant (or value) was found.
	  */
	def llmId(implicit connection: Connection) = pullColumn(model.llmId.column).int
	
	/**
	  * Suffix added to the end of the LLM's name when targeting this specific variant. 
	  * None if no llm size variant (or value) was found.
	  */
	def suffix(implicit connection: Connection) = pullColumn(model.suffix.column).getString
	
	/**
	  * Size of this LLM variant in billions of parameters. None if unknown. 
	  * None if no llm size variant (or value) was found.
	  */
	def size(implicit connection: Connection) = pullColumn(model.size.column).int
	
	/**
	  * Time when this llm size variant was added to the database. 
	  * None if no llm size variant (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this specific variant was removed from this application. 
	  * None if no llm size variant (or value) was found.
	  */
	def removedAfter(implicit connection: Connection) = pullColumn(model.removedAfter.column).instant
	
	/**
	  * Unique id of the accessible llm size variant. None if no llm size variant was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = LlmSizeVariantDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the removed afters of the targeted llm size variants
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any llm size variant was affected
	  */
	def removedAfter_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
}

