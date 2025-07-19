package vf.llama.database.factory.meta

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.meta.PersonaInfoAvailabilityDbModel
import vf.llama.model.partial.meta.PersonaInfoAvailabilityData
import vf.llama.model.stored.meta.PersonaInfoAvailability

import java.time.Instant

/**
  * Used for reading persona info availability data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaInfoAvailabilityDbFactory extends MetaInfoAvailabilityDbFactoryLike[PersonaInfoAvailability]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	override def dbProps = PersonaInfoAvailabilityDbModel
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def table = dbProps.table
	
	/**
	  * @param model Model from which additional data may be read
	  * @param id Id to assign to the read/parsed meta info availability
	  * @param fieldId field id to assign to the new meta info availability
	  * @param contextId context id to assign to the new meta info availability
	  * @param created created to assign to the new meta info availability
	  */
	override protected def apply(model: AnyModel, id: Int, fieldId: Int, contextId: Int, created: Instant) = 
		PersonaInfoAvailability(id, PersonaInfoAvailabilityData(contextId, fieldId, created))
}

