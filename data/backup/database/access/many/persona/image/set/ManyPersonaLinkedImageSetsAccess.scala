package vf.llama.database.access.many.persona.image.set

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaLinkedImageSetDbFactory
import vf.llama.database.storable.persona.PersonaImageSetLinkDbModel
import vf.llama.model.combined.persona.PersonaLinkedImageSet

import java.time.Instant

object ManyPersonaLinkedImageSetsAccess extends ViewFactory[ManyPersonaLinkedImageSetsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaLinkedImageSetsAccess = 
		_ManyPersonaLinkedImageSetsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaLinkedImageSetsAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaLinkedImageSetsAccess
}

/**
  * A common trait for access points that return multiple persona linked image sets at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyPersonaLinkedImageSetsAccess 
	extends ManyPersonaImageSetsAccessLike[PersonaLinkedImageSet, ManyPersonaLinkedImageSetsAccess] 
		with ManyRowModelAccess[PersonaLinkedImageSet]
{
	// COMPUTED	--------------------
	
	/**
	  * persona ids of the accessible persona image set links
	  */
	def linkPersonaIds(implicit connection: Connection) = 
		pullColumn(linkModel.personaId.column).map { v => v.getInt }
	
	/**
	  * image set ids of the accessible persona image set links
	  */
	def linkImageSetIds(implicit connection: Connection) = 
		pullColumn(linkModel.imageSetId.column).map { v => v.getInt }
	
	/**
	  * creation times of the accessible persona image set links
	  */
	def linkCreationTimes(implicit connection: Connection) = 
		pullColumn(linkModel.created.column).map { v => v.getInstant }
	
	/**
	  * removed afters of the accessible persona image set links
	  */
	def linkRemovedAfters(implicit connection: Connection) = 
		pullColumn(linkModel.removedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Model (factory) used for interacting the persona image set links associated 
		with this persona linked image set
	  */
	protected def linkModel = PersonaImageSetLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaLinkedImageSetDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonaLinkedImageSetsAccess = 
		ManyPersonaLinkedImageSetsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the removed afters of the targeted persona image set links
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any persona image set link was affected
	  */
	def linkRemovedAfters_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(linkModel.removedAfter.column, newRemovedAfter)
}

