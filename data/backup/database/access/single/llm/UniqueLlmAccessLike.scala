package vf.llama.database.access.single.llm

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.llm.LlmDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual llms or similar items at a time
  * @tparam A Type of read (llms -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueLlmAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Name of this LLM in Ollama. 
	  * None if no llm (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.name.column).getString
	
	/**
	  * Alias given to this LLM. 
	  * None if no llm (or value) was found.
	  */
	def alias(implicit connection: Connection) = pullColumn(model.alias.column).getString
	
	/**
	  * Time when this llm was added to the database. 
	  * None if no llm (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this LLM was removed from this application. 
	  * None if no llm (or value) was found.
	  */
	def removedAfter(implicit connection: Connection) = pullColumn(model.removedAfter.column).instant
	
	/**
	  * Unique id of the accessible llm. None if no llm was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = LlmDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the aliases of the targeted llms
	  * @param newAlias A new alias to assign
	  * @return Whether any llm was affected
	  */
	def alias_=(newAlias: String)(implicit connection: Connection) = putColumn(model.alias.column, newAlias)
	
	/**
	  * Updates the removed afters of the targeted llms
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any llm was affected
	  */
	def removedAfter_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
}

