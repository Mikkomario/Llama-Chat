package vf.llama.database.access.single.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.persona.PersonaDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual personas or similar items at a time
  * @tparam A Type of read (personas -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Time when this persona was added to the database. 
	  * None if no persona (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this persona was deleted. 
	  * None if no persona (or value) was found.
	  */
	def deletedAfter(implicit connection: Connection) = pullColumn(model.deletedAfter.column).instant
	
	/**
	  * Unique id of the accessible persona. None if no persona was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deleted afters of the targeted personas
	  * @param newDeletedAfter A new deleted after to assign
	  * @return Whether any persona was affected
	  */
	def deletedAfter_=(newDeletedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deletedAfter.column, newDeletedAfter)
}

