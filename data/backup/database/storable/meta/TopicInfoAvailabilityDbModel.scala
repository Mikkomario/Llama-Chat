package vf.llama.database.storable.meta

import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.meta.MetaInfoAvailabilityDbProps
import vf.llama.model.factory.meta.TopicInfoAvailabilityFactory
import vf.llama.model.partial.meta.TopicInfoAvailabilityData
import vf.llama.model.stored.meta.TopicInfoAvailability

import java.time.Instant

/**
  * Used for constructing TopicInfoAvailabilityDbModel instances and for inserting topic info availabilities
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object TopicInfoAvailabilityDbModel 
	extends MetaInfoAvailabilityDbModelFactoryLike[TopicInfoAvailabilityDbModel, TopicInfoAvailability, TopicInfoAvailabilityData] 
		with TopicInfoAvailabilityFactory[TopicInfoAvailabilityDbModel] with MetaInfoAvailabilityDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with topic ids
	  */
	lazy val topicId = property("topicId")
	
	/**
	  * Database property used for interacting with field ids
	  */
	override lazy val fieldId = property("fieldId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	override lazy val created = property("created")
	
	
	// IMPLEMENTED	--------------------
	
	override def contextId = topicId
	
	override def table = LlamaChatTables.topicInfoAvailability
	
	override def apply(data: TopicInfoAvailabilityData) = 
		apply(None, Some(data.topicId), Some(data.fieldId), Some(data.created))
	
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
	  * @param topicId Id of the topic where the linked information is made available
	  * @return A model containing only the specified topic id
	  */
	override def withTopicId(topicId: Int) = apply(topicId = Some(topicId))
	
	override protected def complete(id: Value, data: TopicInfoAvailabilityData) = 
		TopicInfoAvailability(id.getInt, data)
}

/**
  * Used for interacting with TopicInfoAvailabilities in the database
  * @param id topic info availability database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicInfoAvailabilityDbModel(id: Option[Int] = None, topicId: Option[Int] = None, 
	fieldId: Option[Int] = None, created: Option[Instant] = None) 
	extends MetaInfoAvailabilityDbModel with MetaInfoAvailabilityDbModelLike[TopicInfoAvailabilityDbModel] 
		with TopicInfoAvailabilityFactory[TopicInfoAvailabilityDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def contextId = topicId
	
	override def dbProps = TopicInfoAvailabilityDbModel
	
	override def table = TopicInfoAvailabilityDbModel.table
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param fieldId field id to assign to the new model (default = currently assigned value)
	  * @param contextId context id to assign to the new model (default = currently assigned value)
	  * @param created created to assign to the new model (default = currently assigned value)
	  */
	override def copyMetaInfoAvailability(id: Option[Int] = id, fieldId: Option[Int] = fieldId, 
		contextId: Option[Int] = contextId, created: Option[Instant] = created) = 
		copy(id = id, fieldId = fieldId, topicId = contextId, created = created)
	
	/**
	  * @param topicId Id of the topic where the linked information is made available
	  * @return A new copy of this model with the specified topic id
	  */
	override def withTopicId(topicId: Int) = copy(topicId = Some(topicId))
}

