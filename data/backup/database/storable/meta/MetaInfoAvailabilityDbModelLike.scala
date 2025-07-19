package vf.llama.database.storable.meta

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.model.immutable.Storable
import utopia.vault.model.template.{FromIdFactory, HasId}
import vf.llama.database.props.meta.MetaInfoAvailabilityDbProps
import vf.llama.model.factory.meta.MetaInfoAvailabilityFactory

import java.time.Instant

/**
  * Common trait for database models used for interacting with meta info availability data in the database
  * @tparam Repr Type of this DB model
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityDbModelLike[+Repr] 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, Repr] 
		with MetaInfoAvailabilityFactory[Repr]
{
	// ABSTRACT	--------------------
	
	def fieldId: Option[Int]
	
	def contextId: Option[Int]
	
	def created: Option[Instant]
	
	/**
	  * Access to the database properties which are utilized in this model
	  */
	def dbProps: MetaInfoAvailabilityDbProps
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param fieldId field id to assign to the new model (default = currently assigned value)
	  * @param contextId context id to assign to the new model (default = currently assigned value)
	  * @param created created to assign to the new model (default = currently assigned value)
	  * @return Copy of this model with the specified meta info availability properties
	  */
	protected def copyMetaInfoAvailability(id: Option[Int] = id, fieldId: Option[Int] = fieldId, 
		contextId: Option[Int] = contextId, created: Option[Instant] = created): Repr
	
	
	// IMPLEMENTED	--------------------
	
	override def valueProperties = 
		Vector(dbProps.id.name -> id, dbProps.fieldId.name -> fieldId, dbProps.contextId.name -> contextId, 
			dbProps.created.name -> created)
	
	/**
	  * @param contextId Id of the context where the linked information is made available
	  * @return A new copy of this model with the specified context id
	  */
	override def withContextId(contextId: Int) = copyMetaInfoAvailability(contextId = Some(contextId))
	
	/**
	  * @param created Time when this information was made available
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copyMetaInfoAvailability(created = Some(created))
	
	/**
	  * @param fieldId Id of the meta info -field made available
	  * @return A new copy of this model with the specified field id
	  */
	override def withFieldId(fieldId: Int) = copyMetaInfoAvailability(fieldId = Some(fieldId))
	
	override def withId(id: Int) = copyMetaInfoAvailability(id = Some(id))
}

