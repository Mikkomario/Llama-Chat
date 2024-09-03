package vf.llama.database.factory.meta

import utopia.flow.generic.model.immutable.Model
import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.llama.database.props.meta.MetaInfoAvailabilityDbProps

import java.time.Instant

/**
  * Common trait for factories which parse meta info availability data from database-originated models
  * @tparam A Type of read instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityDbFactoryLike[+A] extends FromValidatedRowModelFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * Database properties used when parsing column data
	  */
	def dbProps: MetaInfoAvailabilityDbProps
	
	/**
	  * @param model Model from which additional data may be read
	  * @param id Id to assign to the read/parsed meta info availability
	  * @param fieldId field id to assign to the new meta info availability
	  * @param contextId context id to assign to the new meta info availability
	  * @param created created to assign to the new meta info availability
	  * @return A meta info availability with the specified data
	  */
	protected def apply(model: AnyModel, id: Int, fieldId: Int, contextId: Int, created: Instant): A
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		apply(valid, valid(dbProps.id.name).getInt, valid(dbProps.fieldId.name).getInt, 
			valid(dbProps.contextId.name).getInt, valid(dbProps.created.name).getInstant)
}

