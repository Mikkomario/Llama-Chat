package vf.llama.database.access.many.llm

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.llm.LlmDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple llms or similar instances at a time
  * @tparam A Type of read (llms -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyLlmsAccessLike[+A, +Repr] extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * names of the accessible llms
	  */
	def names(implicit connection: Connection) = pullColumn(model.name.column).flatMap { _.string }
	
	/**
	  * aliases of the accessible llms
	  */
	def aliases(implicit connection: Connection) = pullColumn(model.alias.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible llms
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * removed afters of the accessible llms
	  */
	def removedAfters(implicit connection: Connection) = 
		pullColumn(model.removedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible llms
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
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
	def aliases_=(newAlias: String)(implicit connection: Connection) = putColumn(model.alias.column, newAlias)
	
	/**
	  * Updates the removed afters of the targeted llms
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any llm was affected
	  */
	def removedAfters_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
}

