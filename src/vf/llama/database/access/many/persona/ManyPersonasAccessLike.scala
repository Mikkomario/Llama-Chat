package vf.llama.database.access.many.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.persona.PersonaDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple personas or similar instances at a time
  * @tparam A Type of read (personas -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonasAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * creation times of the accessible personas
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * deleted afters of the accessible personas
	  */
	def deletedAfters(implicit connection: Connection) = 
		pullColumn(model.deletedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible personas
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
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
	def deletedAfters_=(newDeletedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deletedAfter.column, newDeletedAfter)
}

