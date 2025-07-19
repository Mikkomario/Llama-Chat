package vf.llama.database.access.single.persona.image.link

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaImageSetLinkDbFactory
import vf.llama.database.storable.persona.PersonaImageSetLinkDbModel
import vf.llama.model.stored.persona.PersonaImageSetLink

import java.time.Instant

object UniquePersonaImageSetLinkAccess extends ViewFactory[UniquePersonaImageSetLinkAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaImageSetLinkAccess = 
		_UniquePersonaImageSetLinkAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaImageSetLinkAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaImageSetLinkAccess
}

/**
  * A common trait for access points that return individual and distinct persona image set links.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaImageSetLinkAccess 
	extends SingleRowModelAccess[PersonaImageSetLink] 
		with DistinctModelAccess[PersonaImageSetLink, Option[PersonaImageSetLink], Value] 
		with NullDeprecatableView[UniquePersonaImageSetLinkAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the persona for which the linked images are used. 
	  * None if no persona image set link (or value) was found.
	  */
	def personaId(implicit connection: Connection) = pullColumn(model.personaId.column).int
	
	/**
	  * Id of the image set used for the linked persona. 
	  * None if no persona image set link (or value) was found.
	  */
	def imageSetId(implicit connection: Connection) = pullColumn(model.imageSetId.column).int
	
	/**
	  * Time when this persona image set link was added to the database. 
	  * None if no persona image set link (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this link was removed. 
	  * None if no persona image set link (or value) was found.
	  */
	def removedAfter(implicit connection: Connection) = pullColumn(model.removedAfter.column).instant
	
	/**
	  * Unique id of the accessible persona image set link. None if no persona image set link was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaImageSetLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaImageSetLinkDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaImageSetLinkAccess = 
		UniquePersonaImageSetLinkAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the removed afters of the targeted persona image set links
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any persona image set link was affected
	  */
	def removedAfter_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
}

