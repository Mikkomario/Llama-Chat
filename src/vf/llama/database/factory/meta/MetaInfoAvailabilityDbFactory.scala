package vf.llama.database.factory.meta

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.immutable.Table
import utopia.vault.sql.OrderBy
import vf.llama.database.props.meta.MetaInfoAvailabilityDbProps
import vf.llama.model.partial.meta.MetaInfoAvailabilityData
import vf.llama.model.stored.meta.MetaInfoAvailability

import java.time.Instant

object MetaInfoAvailabilityDbFactory
{
	// OTHER	--------------------
	
	/**
	  * @param table Table from which data is read
	  * @param dbProps Database properties used when reading column data
	  * @return A factory used for parsing meta info availabilities from database model data
	  */
	def apply(table: Table, dbProps: MetaInfoAvailabilityDbProps): MetaInfoAvailabilityDbFactory = 
		_MetaInfoAvailabilityDbFactory(table, dbProps)
	
	
	// NESTED	--------------------
	
	/**
	  * @param table Table from which data is read
	  * @param dbProps Database properties used when reading column data
	  */
	private case class _MetaInfoAvailabilityDbFactory(table: Table, dbProps: MetaInfoAvailabilityDbProps) 
		extends MetaInfoAvailabilityDbFactory
	{
		// IMPLEMENTED	--------------------
		
		override def defaultOrdering: Option[OrderBy] = None
		
		/**
		  * @param model Model from which additional data may be read
		  * @param id Id to assign to the read/parsed meta info availability
		  * @param fieldId field id to assign to the new meta info availability
		  * @param contextId context id to assign to the new meta info availability
		  * @param created created to assign to the new meta info availability
		  */
		override protected def apply(model: AnyModel, id: Int, fieldId: Int, contextId: Int, 
			created: Instant) = 
			MetaInfoAvailability(id, MetaInfoAvailabilityData(fieldId, contextId, created))
	}
}

/**
  * Common trait for factories which parse meta info availability data from database-originated models
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityDbFactory extends MetaInfoAvailabilityDbFactoryLike[MetaInfoAvailability]

