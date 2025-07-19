package vf.llama.database.access.many.persona.image

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{FilterableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaImageDbFactory
import vf.llama.database.storable.persona.PersonaImageDbModel
import vf.llama.model.stored.persona.PersonaImage

import scala.concurrent.duration.FiniteDuration

import java.util.concurrent.TimeUnit

object ManyPersonaImagesAccess extends ViewFactory[ManyPersonaImagesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override
		 def apply(condition: Condition): ManyPersonaImagesAccess = _ManyPersonaImagesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaImagesAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaImagesAccess
}

/**
  * A common trait for access points which target multiple persona images at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaImagesAccess 
	extends ManyRowModelAccess[PersonaImage] with FilterableView[ManyPersonaImagesAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * animation ids of the accessible persona images
	  */
	def animationIds(implicit connection: Connection) = pullColumn(model.animationId.column)
		.map { v => v.getInt }
	
	/**
	  * file names of the accessible persona images
	  */
	def fileNames(implicit connection: Connection) = pullColumn(model.fileName.column).flatMap { _.string }
	
	/**
	  * order indexs of the accessible persona images
	  */
	def orderIndexs(implicit connection: Connection) = pullColumn(model.orderIndex.column)
		.map { v => v.getInt }
	
	/**
	  * custom durations of the accessible persona images
	  */
	def customDurations(implicit connection: Connection) = 
		pullColumn(model.customDuration.column).flatMap { v => v.long.map { FiniteDuration(_, 
			TimeUnit.MILLISECONDS) } }
	
	/**
	  * Unique ids of the accessible persona images
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaImageDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaImageDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonaImagesAccess = ManyPersonaImagesAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param orderIndex order index to target
	  * @return Copy of this access point that only includes persona images with the specified order index
	  */
	def withOrderIndex(orderIndex: Int) = filter(model.orderIndex.column <=> orderIndex)
	
	/**
	  * @param orderIndexs Targeted order indexs
	  * @return Copy of this access point that only includes persona images where order index is within the
	  *  specified value set
	  */
	def withOrderIndexs(orderIndexs: IterableOnce[Int]) = 
		filter(model.orderIndex.column.in(IntSet.from(orderIndexs)))
	
	/**
	  * @param animationId animation id to target
	  * @return Copy of this access point that only includes persona images with the specified animation id
	  */
	def withinAnimation(animationId: Int) = filter(model.animationId.column <=> animationId)
	
	/**
	  * @param animationIds Targeted animation ids
	  * @return Copy of this access point that only includes persona images where animation id is within the
	  *  specified value set
	  */
	def withinAnimations(animationIds: IterableOnce[Int]) = 
		filter(model.animationId.column.in(IntSet.from(animationIds)))
}

