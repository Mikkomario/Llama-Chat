package vf.llama.database.access.many.persona.image.link

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaImageSetLinkDbFactory
import vf.llama.database.storable.persona.PersonaImageSetLinkDbModel
import vf.llama.model.stored.persona.PersonaImageSetLink

import java.time.Instant

object ManyPersonaImageSetLinksAccess extends ViewFactory[ManyPersonaImageSetLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaImageSetLinksAccess = 
		_ManyPersonaImageSetLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaImageSetLinksAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaImageSetLinksAccess
}

/**
  * A common trait for access points which target multiple persona image set links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaImageSetLinksAccess 
	extends ManyRowModelAccess[PersonaImageSetLink] with NullDeprecatableView[ManyPersonaImageSetLinksAccess] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * persona ids of the accessible persona image set links
	  */
	def personaIds(implicit connection: Connection) = pullColumn(model.personaId.column).map { v => v.getInt }
	
	/**
	  * image set ids of the accessible persona image set links
	  */
	def imageSetIds(implicit connection: Connection) = pullColumn(model.imageSetId.column)
		.map { v => v.getInt }
	
	/**
	  * creation times of the accessible persona image set links
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * removed afters of the accessible persona image set links
	  */
	def removedAfters(implicit connection: Connection) = 
		pullColumn(model.removedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible persona image set links
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaImageSetLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaImageSetLinkDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonaImageSetLinksAccess = 
		ManyPersonaImageSetLinksAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param personaId persona id to target
	  * @return Copy of this access point that only includes persona image set links 
		with the specified persona id
	  */
	def forPersona(personaId: Int) = filter(model.personaId.column <=> personaId)
	
	/**
	  * @param personaIds Targeted persona ids
	  * 
		@return Copy of this access point that only includes persona image set links where persona id is within the
	  *  specified value set
	  */
	def forPersonas(personaIds: IterableOnce[Int]) = filter(model
		.personaId.column.in(IntSet.from(personaIds)))
	
	/**
	  * Updates the removed afters of the targeted persona image set links
	  * @param newRemovedAfter A new removed after to assign
	  * @return Whether any persona image set link was affected
	  */
	def removedAfters_=(newRemovedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.removedAfter.column, newRemovedAfter)
	
	/**
	  * @param imageSetId image set id to target
	  * @return Copy of this access point that only includes persona image set links 
		with the specified image set id
	  */
	def usingImageSet(imageSetId: Int) = filter(model.imageSetId.column <=> imageSetId)
	
	/**
	  * @param imageSetIds Targeted image set ids
	  * 
		@return Copy of this access point that only includes persona image set links where image set id is within
	  *  the specified value set
	  */
	def usingImageSets(imageSetIds: IterableOnce[Int]) = 
		filter(model.imageSetId.column.in(IntSet.from(imageSetIds)))
}

