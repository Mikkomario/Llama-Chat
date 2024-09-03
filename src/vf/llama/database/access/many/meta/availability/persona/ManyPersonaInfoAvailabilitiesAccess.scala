package vf.llama.database.access.many.meta.availability.persona

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.many.meta.availability.ManyMetaInfoAvailabilitiesAccessLike
import vf.llama.database.factory.meta.PersonaInfoAvailabilityDbFactory
import vf.llama.database.storable.meta.PersonaInfoAvailabilityDbModel
import vf.llama.model.stored.meta.PersonaInfoAvailability

object ManyPersonaInfoAvailabilitiesAccess extends ViewFactory[ManyPersonaInfoAvailabilitiesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaInfoAvailabilitiesAccess = 
		_ManyPersonaInfoAvailabilitiesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaInfoAvailabilitiesAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaInfoAvailabilitiesAccess
}

/**
  * A common trait for access points which target multiple persona info availabilities at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaInfoAvailabilitiesAccess 
	extends ManyMetaInfoAvailabilitiesAccessLike[PersonaInfoAvailability, ManyPersonaInfoAvailabilitiesAccess] 
		with ManyRowModelAccess[PersonaInfoAvailability]
{
	// COMPUTED	--------------------
	
	/**
	  * persona ids of the accessible persona info availabilities
	  */
	def personaIds(implicit connection: Connection) = contextIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaInfoAvailabilityDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = PersonaInfoAvailabilityDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonaInfoAvailabilitiesAccess = 
		ManyPersonaInfoAvailabilitiesAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param personaId persona id to target
	  * @return Copy of this access point that only includes persona info availabilities 
		with the specified persona id
	  */
	def forPersona(personaId: Int) = filter(model.personaId.column <=> personaId)
	
	/**
	  * @param personaIds Targeted persona ids
	  * 
		@return Copy of this access point that only includes persona info availabilities where persona id is within
	  *  the specified value set
	  */
	def forPersonas(personaIds: IterableOnce[Int]) = filter(model
		.personaId.column.in(IntSet.from(personaIds)))
}

