package vf.llama.database.access.many.persona.image.set

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.parse.file.FileExtensions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.persona.PersonaImageSetDbModel

import java.nio.file.Path
import java.time.Instant

/**
  * A common trait for access points which target multiple persona image sets or similar instances at a time
  * @tparam A Type of read (persona image sets -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaImageSetsAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * relative directories of the accessible persona image sets
	  */
	def relativeDirectories(implicit connection: Connection) = 
		pullColumn(model.relativeDirectory.column).flatMap { _.string }.map { v => v: Path }
	
	/**
	  * creation times of the accessible persona image sets
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * removed afters of the accessible persona image sets
	  */
	def removedAfters(implicit connection: Connection) = 
		pullColumn(model.removedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible persona image sets
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
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
	def removedAfters_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
	
	/**
	  * @param relativeDirectories Targeted relative directories
	  * 
		@return Copy of this access point that only includes persona image sets where relative directory is within
	  *  the specified value set
	  */
	def usingDirectories(relativeDirectories: Iterable[Path]) = 
		filter(model.relativeDirectory.column.in(relativeDirectories
			.map { relativeDirectory => relativeDirectory.toJson }))
	
	/**
	  * @param relativeDirectory relative directory to target
	  * @return Copy of this access point that only includes persona image sets 
		with the specified relative directory
	  */
	def usingDirectory(relativeDirectory: Path) = 
		filter(model.relativeDirectory.column <=> relativeDirectory.toJson)
}

