package vf.llama.database.storable.meta

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.meta.MetaInfoAvailabilityDbProps
import vf.llama.model.factory.meta.ThreadInfoAvailabilityFactory
import vf.llama.model.partial.meta.ThreadInfoAvailabilityData
import vf.llama.model.stored.meta.ThreadInfoAvailability

import java.time.Instant

/**
  * Used for constructing ThreadInfoAvailabilityDbModel instances and for inserting thread info availabilities
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ThreadInfoAvailabilityDbModel 
	extends MetaInfoAvailabilityDbModelFactoryLike[ThreadInfoAvailabilityDbModel, ThreadInfoAvailability, ThreadInfoAvailabilityData] 
		with ThreadInfoAvailabilityFactory[ThreadInfoAvailabilityDbModel] with MetaInfoAvailabilityDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with thread ids
	  */
	lazy val threadId = property("threadId")
	
	/**
	  * Database property used for interacting with field ids
	  */
	override lazy val fieldId = property("fieldId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	override lazy val created = property("created")
	
	
	// IMPLEMENTED	--------------------
	
	override def contextId = threadId
	
	override def table = LlamaChatTables.threadInfoAvailability
	
	override def apply(data: ThreadInfoAvailabilityData) = 
		apply(None, Some(data.threadId), Some(data.fieldId), Some(data.created))
	
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
	
	/**
	  * @param threadId Id of the thread where the linked information is made available
	  * @return A model containing only the specified thread id
	  */
	override def withThreadId(threadId: Int) = apply(threadId = Some(threadId))
	
	override protected def complete(id: Value, data: ThreadInfoAvailabilityData) = 
		ThreadInfoAvailability(id.getInt, data)
}

/**
  * Used for interacting with ThreadInfoAvailabilities in the database
  * @param id thread info availability database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ThreadInfoAvailabilityDbModel(id: Option[Int] = None, threadId: Option[Int] = None, 
	fieldId: Option[Int] = None, created: Option[Instant] = None) 
	extends MetaInfoAvailabilityDbModel with MetaInfoAvailabilityDbModelLike[ThreadInfoAvailabilityDbModel] 
		with ThreadInfoAvailabilityFactory[ThreadInfoAvailabilityDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def contextId = threadId
	
	override def dbProps = ThreadInfoAvailabilityDbModel
	
	override def table = ThreadInfoAvailabilityDbModel.table
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param fieldId field id to assign to the new model (default = currently assigned value)
	  * @param contextId context id to assign to the new model (default = currently assigned value)
	  * @param created created to assign to the new model (default = currently assigned value)
	  */
	override def copyMetaInfoAvailability(id: Option[Int] = id, fieldId: Option[Int] = fieldId, 
		contextId: Option[Int] = contextId, created: Option[Instant] = created) = 
		copy(id = id, fieldId = fieldId, threadId = contextId, created = created)
	
	/**
	  * @param threadId Id of the thread where the linked information is made available
	  * @return A new copy of this model with the specified thread id
	  */
	override def withThreadId(threadId: Int) = copy(threadId = Some(threadId))
}

