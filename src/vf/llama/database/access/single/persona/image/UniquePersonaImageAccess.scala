package vf.llama.database.access.single.persona.image

import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{FilterableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaImageDbFactory
import vf.llama.database.storable.persona.PersonaImageDbModel
import vf.llama.model.stored.persona.PersonaImage

import scala.concurrent.duration.FiniteDuration

import java.util.concurrent.TimeUnit

object UniquePersonaImageAccess extends ViewFactory[UniquePersonaImageAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaImageAccess = 
		_UniquePersonaImageAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaImageAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaImageAccess
}

/**
  * A common trait for access points that return individual and distinct persona images.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaImageAccess 
	extends SingleRowModelAccess[PersonaImage] 
		with DistinctModelAccess[PersonaImage, Option[PersonaImage], Value] 
		with FilterableView[UniquePersonaImageAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the animation this image is part of. 
	  * None if no persona image (or value) was found.
	  */
	def animationId(implicit connection: Connection) = pullColumn(model.animationId.column).int
	
	/**
	  * Name of the wrapped image file. 
	  * None if no persona image (or value) was found.
	  */
	def fileName(implicit connection: Connection) = pullColumn(model.fileName.column).getString
	
	/**
	  * 0-based index of this image within its animation. 
	  * None if no persona image (or value) was found.
	  */
	def orderIndex(implicit connection: Connection) = pullColumn(model.orderIndex.column).int
	
	/**
	  * Duration how long this image / frame should be displayed at once. 
	  * None if the default duration should be applied. 
	  * None if no persona image (or value) was found.
	  */
	def customDuration(implicit connection: Connection) = 
		pullColumn(model.customDuration.column).long.map { FiniteDuration(_, TimeUnit.MILLISECONDS) }
	
	/**
	  * Unique id of the accessible persona image. None if no persona image was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaImageDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaImageDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaImageAccess = UniquePersonaImageAccess(condition)
}

