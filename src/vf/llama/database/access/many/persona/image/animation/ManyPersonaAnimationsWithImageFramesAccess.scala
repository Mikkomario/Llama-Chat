package vf.llama.database.access.many.persona.image.animation

import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaAnimationWithFramesDbFactory
import vf.llama.database.storable.persona.PersonaImageDbModel
import vf.llama.model.combined.persona.PersonaAnimationWithFrames

import scala.concurrent.duration.FiniteDuration

import java.util.concurrent.TimeUnit

object ManyPersonaAnimationsWithImageFramesAccess 
	extends ViewFactory[ManyPersonaAnimationsWithImageFramesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaAnimationsWithImageFramesAccess = 
		_ManyPersonaAnimationsWithImageFramesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaAnimationsWithImageFramesAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManyPersonaAnimationsWithImageFramesAccess
}

/**
  * A common trait for access points that return multiple persona animations with image frames at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyPersonaAnimationsWithImageFramesAccess 
	extends ManyPersonaAnimationsAccessLike[PersonaAnimationWithFrames, 
		ManyPersonaAnimationsWithImageFramesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * animation ids of the accessible persona images
	  */
	def frameAnimationIds(implicit connection: Connection) = 
		pullColumn(frameModel.animationId.column).map { v => v.getInt }
	
	/**
	  * file names of the accessible persona images
	  */
	def frameFileNames(implicit connection: Connection) = 
		pullColumn(frameModel.fileName.column).flatMap { _.string }
	
	/**
	  * order indexs of the accessible persona images
	  */
	def frameOrderIndexs(implicit connection: Connection) = 
		pullColumn(frameModel.orderIndex.column).map { v => v.getInt }
	
	/**
	  * custom durations of the accessible persona images
	  */
	def frameCustomDurations(implicit connection: Connection) = 
		pullColumn(frameModel.customDuration.column).flatMap { v => v.long.map { FiniteDuration(_, 
			TimeUnit.MILLISECONDS) } }
	
	/**
	  * Model (factory) used for interacting the persona images associated with this persona animation 
		with frames
	  */
	protected def frameModel = PersonaImageDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaAnimationWithFramesDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonaAnimationsWithImageFramesAccess = 
		ManyPersonaAnimationsWithImageFramesAccess(condition)
}

