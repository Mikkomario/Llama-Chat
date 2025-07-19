package vf.llama.database.access.many.persona.image.animation

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.parse.file.FileExtensions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.persona.PersonaAnimationDbModel

import scala.concurrent.duration.FiniteDuration

import java.nio.file.Path
import java.util.concurrent.TimeUnit

/**
  * A common trait for access points which target multiple persona animations or similar instances at a time
  * @tparam A Type of read (persona animations -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaAnimationsAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with FilterableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * set ids of the accessible persona animations
	  */
	def setIds(implicit connection: Connection) = pullColumn(model.setId.column).map { v => v.getInt }
	
	/**
	  * relative sub directories of the accessible persona animations
	  */
	def relativeSubDirectories(implicit connection: Connection) = 
		pullColumn(model.relativeSubDirectory.column).flatMap { _.string }.map { v => v: Path }
	
	/**
	  * default frame durations of the accessible persona animations
	  */
	def defaultFrameDurations(implicit connection: Connection) = 
		pullColumn(model.defaultFrameDuration.column).flatMap { v => v.long.map { FiniteDuration(_, 
			TimeUnit.MILLISECONDS) } }
	
	/**
	  * Unique ids of the accessible persona animations
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaAnimationDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * @param setId set id to target
	  * @return Copy of this access point that only includes persona animations with the specified set id
	  */
	def withinImageSet(setId: Int) = filter(model.setId.column <=> setId)
	
	/**
	  * @param setIds Targeted set ids
	  * @return Copy of this access point that only includes persona animations where set id is within the
	  *  specified value set
	  */
	def withinImageSets(setIds: IterableOnce[Int]) = filter(model.setId.column.in(IntSet.from(setIds)))
}

