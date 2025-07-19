package vf.llama.database.access.single.persona.image.animation

import utopia.flow.generic.model.immutable.Value
import utopia.flow.parse.file.FileExtensions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.persona.PersonaAnimationDbModel

import scala.concurrent.duration.FiniteDuration

import java.nio.file.Path
import java.util.concurrent.TimeUnit

/**
  * A common trait for access points which target individual persona animations or similar items at a time
  * @tparam A Type of read (persona animations -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaAnimationAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the set to which this animation belongs. 
	  * None if no persona animation (or value) was found.
	  */
	def setId(implicit connection: Connection) = pullColumn(model.setId.column).int
	
	/**
	  * Path to the directory that contains image files specific to this animation. 
	  * Relative to the image set's directory. 
	  * None if no persona animation (or value) was found.
	  */
	def relativeSubDirectory(implicit connection: Connection) = 
		Some(pullColumn(model.relativeSubDirectory.column).getString: Path)
	
	/**
	  * Duration how long each frame will be shown by default. 
	  * None if the common default should be used instead. 
	  * None if no persona animation (or value) was found.
	  */
	def defaultFrameDuration(implicit connection: Connection) = 
		pullColumn(model.defaultFrameDuration.column).long.map { FiniteDuration(_, TimeUnit.MILLISECONDS) }
	
	/**
	  * Unique id of the accessible persona animation. None if no persona animation was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaAnimationDbModel
}

