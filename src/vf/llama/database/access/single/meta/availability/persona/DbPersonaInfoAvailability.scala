package vf.llama.database.access.single.meta.availability.persona

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.PersonaInfoAvailabilityDbFactory
import vf.llama.database.storable.meta.PersonaInfoAvailabilityDbModel
import vf.llama.model.stored.meta.PersonaInfoAvailability

/**
  * Used for accessing individual persona info availabilities
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaInfoAvailability 
	extends SingleRowModelAccess[PersonaInfoAvailability] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaInfoAvailabilityDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaInfoAvailabilityDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona info availability
	  * @return An access point to that persona info availability
	  */
	def apply(id: Int) = DbSinglePersonaInfoAvailability(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona info availabilities.
	  * @return An access point to the persona info availability that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniquePersonaInfoAvailabilityAccess(condition)
}

