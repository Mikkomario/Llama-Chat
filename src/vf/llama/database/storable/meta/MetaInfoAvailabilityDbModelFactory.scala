package vf.llama.database.storable.meta

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Table}
import vf.llama.database.props.meta.{MetaInfoAvailabilityDbProps, MetaInfoAvailabilityDbPropsWrapper}
import vf.llama.model.partial.meta.MetaInfoAvailabilityData
import vf.llama.model.stored.meta.MetaInfoAvailability

import java.time.Instant

object MetaInfoAvailabilityDbModelFactory
{
	// OTHER	--------------------
	
	/**
	  * @return A factory for constructing meta info availability database models
	  */
	def apply(table: Table, dbProps: MetaInfoAvailabilityDbProps) = 
		MetaInfoAvailabilityDbModelFactoryImpl(table, dbProps)
	
	
	// NESTED	--------------------
	
	/**
	  * Used for constructing MetaInfoAvailabilityDbModel instances and for inserting meta info availabilities
	  *  to the database
	  * @param table Table targeted by these models
	  * 
		@param metaInfoAvailabilityDbProps Properties which specify how the database interactions are performed
	  * @author Mikko Hilpinen
	  * @since 01.09.2024, v0.1
	  */
	case class MetaInfoAvailabilityDbModelFactoryImpl(table: Table, 
		metaInfoAvailabilityDbProps: MetaInfoAvailabilityDbProps) 
		extends MetaInfoAvailabilityDbModelFactory with MetaInfoAvailabilityDbPropsWrapper
	{
		// ATTRIBUTES	--------------------
		
		override lazy val id = DbPropertyDeclaration("id", index)
		
		
		// IMPLEMENTED	--------------------
		
		override def apply(data: MetaInfoAvailabilityData) = 
			apply(None, Some(data.fieldId), Some(data.contextId), Some(data.created))
		
		/**
		  * @param contextId Id of the context where the linked information is made available
		  * @return A model containing only the specified context id
		  */
		override def withContextId(contextId: Int) = apply(contextId = Some(contextId))
		
		/**
		  * @param created Time when this information was made available
		  * @return A model containing only the specified created
		  */
		override def withCreated(created: Instant) = apply(created = Some(created))
		
		/**
		  * @param fieldId Id of the meta info -field made available
		  * @return A model containing only the specified field id
		  */
		override def withFieldId(fieldId: Int) = apply(fieldId = Some(fieldId))
		
		override def withId(id: Int) = apply(id = Some(id))
		
		override protected def complete(id: Value, data: MetaInfoAvailabilityData) = 
			MetaInfoAvailability(id.getInt, data)
		
		
		// OTHER	--------------------
		
		/**
		  * @param id meta info availability database id
		  * @return Constructs a new meta info availability database model with the specified properties
		  */
		def apply(id: Option[Int] = None, fieldId: Option[Int] = None, contextId: Option[Int] = None, 
			created: Option[Instant] = None): MetaInfoAvailabilityDbModel = 
			_MetaInfoAvailabilityDbModel(table, metaInfoAvailabilityDbProps, id, fieldId, contextId, created)
	}
	
	/**
	  * Used for interacting with MetaInfoAvailabilities in the database
	  * @param table Table interacted with when using this model
	  * @param dbProps Configurations of the interacted database properties
	  * @param id meta info availability database id
	  * @author Mikko Hilpinen
	  * @since 01.09.2024, v0.1
	  */
	private case class _MetaInfoAvailabilityDbModel(table: Table, dbProps: MetaInfoAvailabilityDbProps, 
		id: Option[Int] = None, fieldId: Option[Int] = None, contextId: Option[Int] = None, 
		created: Option[Instant] = None) 
		extends MetaInfoAvailabilityDbModel
	{
		// IMPLEMENTED	--------------------
		
		/**
		  * @param id Id to assign to the new model (default = currently assigned id)
		  * @param fieldId field id to assign to the new model (default = currently assigned value)
		  * @param contextId context id to assign to the new model (default = currently assigned value)
		  * @param created created to assign to the new model (default = currently assigned value)
		  * @return Copy of this model with the specified meta info availability properties
		  */
		override protected def copyMetaInfoAvailability(id: Option[Int] = id, fieldId: Option[Int] = fieldId, 
			contextId: Option[Int] = contextId, created: Option[Instant] = created) = 
			copy(id = id, fieldId = fieldId, contextId = contextId, created = created)
	}
}

/**
  * Common trait for factories yielding meta info availability database models
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityDbModelFactory 
	extends MetaInfoAvailabilityDbModelFactoryLike[MetaInfoAvailabilityDbModel, MetaInfoAvailability, 
		MetaInfoAvailabilityData]

