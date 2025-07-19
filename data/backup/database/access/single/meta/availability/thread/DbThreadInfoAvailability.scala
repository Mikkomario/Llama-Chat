package vf.llama.database.access.single.meta.availability.thread

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.meta.ThreadInfoAvailabilityDbFactory
import vf.llama.database.storable.meta.ThreadInfoAvailabilityDbModel
import vf.llama.model.stored.meta.ThreadInfoAvailability

/**
  * Used for accessing individual thread info availabilities
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbThreadInfoAvailability 
	extends SingleRowModelAccess[ThreadInfoAvailability] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = ThreadInfoAvailabilityDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ThreadInfoAvailabilityDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted thread info availability
	  * @return An access point to that thread info availability
	  */
	def apply(id: Int) = DbSingleThreadInfoAvailability(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique thread info availabilities.
	  * @return An access point to the thread info availability that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueThreadInfoAvailabilityAccess(condition)
}

