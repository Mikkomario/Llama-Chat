package vf.llama.model.partial.meta

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.time.Now
import vf.llama.model.factory.meta.TopicInfoAvailabilityFactory

import java.time.Instant

object TopicInfoAvailabilityData extends FromModelFactoryWithSchema[TopicInfoAvailabilityData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("topicId", IntType, Vector("contextId", "context_id", 
			"topic_id")), PropertyDeclaration("fieldId", IntType, Single("field_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		TopicInfoAvailabilityData(valid("topicId").getInt, valid("fieldId").getInt, 
			valid("created").getInstant)
}

/**
  * A link that makes a meta info field available within a specific topic
  * @param topicId Id of the topic where the linked information is made available
  * @param fieldId Id of the meta info -field made available
  * @param created Time when this information was made available
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicInfoAvailabilityData(topicId: Int, fieldId: Int, created: Instant = Now) 
	extends TopicInfoAvailabilityFactory[TopicInfoAvailabilityData] with MetaInfoAvailabilityData 
		with MetaInfoAvailabilityDataLike[TopicInfoAvailabilityData]
{
	// IMPLEMENTED	--------------------
	
	override def contextId = topicId
	
	override def copyMetaInfoAvailability(fieldId: Int, contextId: Int, created: Instant) = 
		copy(topicId = contextId, fieldId = fieldId, created = created)
	
	override def withTopicId(topicId: Int) = copy(topicId = topicId)
}

