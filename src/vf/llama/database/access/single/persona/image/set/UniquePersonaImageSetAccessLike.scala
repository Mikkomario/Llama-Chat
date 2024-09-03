package vf.llama.database.access.single.persona.image.set

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.parse.file.FileExtensions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.persona.PersonaImageSetDbModel

import java.nio.file.Path
import java.time.Instant

/**
  * A common trait for access points which target individual persona image sets or similar items at a time
  * @tparam A Type of read (persona image sets -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaImageSetAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Path to the directory that contains the images used in this image set. 
	  * Relative to the main image directory. 
	  * None if no persona image set (or value) was found.
	  */
	def relativeDirectory(implicit connection: Connection) = 
		Some(pullColumn(model.relativeDirectory.column).getString: Path)
	
	/**
	  * Time when this persona image set was added to the database. 
	  * None if no persona image set (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this image set was removed or deleted. 
	  * None if no persona image set (or value) was found.
	  */
	def removedAfter(implicit connection: Connection) = pullColumn(model.removedAfter.column).instant
	
	/**
	  * Unique id of the accessible persona image set. None if no persona image set was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaImageSetDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the removed afters of the targeted persona image sets
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any persona image set was affected
	  */
	def removedAfter_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
}

