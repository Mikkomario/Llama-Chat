package vf.llama.database.access.single.persona.image.set

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaLinkedImageSetDbFactory
import vf.llama.database.storable.persona.PersonaImageSetLinkDbModel
import vf.llama.model.combined.persona.PersonaLinkedImageSet

import java.time.Instant

object UniquePersonaLinkedImageSetAccess extends ViewFactory[UniquePersonaLinkedImageSetAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaLinkedImageSetAccess = 
		_UniquePersonaLinkedImageSetAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaLinkedImageSetAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaLinkedImageSetAccess
}

/**
  * A common trait for access points that return distinct persona linked image sets
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaLinkedImageSetAccess 
	extends UniquePersonaImageSetAccessLike[PersonaLinkedImageSet, UniquePersonaLinkedImageSetAccess] 
		with SingleChronoRowModelAccess[PersonaLinkedImageSet, UniquePersonaLinkedImageSetAccess] 
		with NullDeprecatableView[UniquePersonaLinkedImageSetAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the persona for which the linked images are used. 
	  * None if no persona image set link (or value) was found.
	  */
	def linkPersonaId(implicit connection: Connection) = pullColumn(linkModel.personaId.column).int
	
	/**
	  * Id of the image set used for the linked persona. 
	  * None if no persona image set link (or value) was found.
	  */
	def linkImageSetId(implicit connection: Connection) = pullColumn(linkModel.imageSetId.column).int
	
	/**
	  * Time when this persona image set link was added to the database. 
	  * None if no persona image set link (or value) was found.
	  */
	def linkCreated(implicit connection: Connection) = pullColumn(linkModel.created.column).instant
	
	/**
	  * Time when this link was removed. 
	  * None if no persona image set link (or value) was found.
	  */
	def linkRemovedAfter(implicit connection: Connection) = pullColumn(linkModel.removedAfter.column).instant
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = PersonaImageSetLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaLinkedImageSetDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaLinkedImageSetAccess = 
		UniquePersonaLinkedImageSetAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the removed afters of the targeted persona image set links
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any persona image set link was affected
	  */
	def linkRemovedAfter_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(linkModel.removedAfter.column, newRemovedAfter)
}

