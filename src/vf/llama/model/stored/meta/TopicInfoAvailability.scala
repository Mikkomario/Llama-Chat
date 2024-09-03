package vf.llama.model.stored.meta

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.meta.availability.topic.DbSingleTopicInfoAvailability
import vf.llama.model.factory.meta.TopicInfoAvailabilityFactoryWrapper
import vf.llama.model.partial.meta.{MetaInfoAvailabilityData, TopicInfoAvailabilityData}

object TopicInfoAvailability extends StoredFromModelFactory[TopicInfoAvailabilityData, TopicInfoAvailability]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = TopicInfoAvailabilityData
	
	override protected def complete(model: AnyModel, data: TopicInfoAvailabilityData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a topic info availability that has already been stored in the database
  * @param id id of this topic info availability in the database
  * @param data Wrapped topic info availability data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicInfoAvailability(id: Int, data: TopicInfoAvailabilityData) 
	extends TopicInfoAvailabilityFactoryWrapper[TopicInfoAvailabilityData, TopicInfoAvailability] 
		with MetaInfoAvailabilityData 
		with StoredMetaInfoAvailabilityLike[TopicInfoAvailabilityData, TopicInfoAvailability]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this topic info availability in the database
	  */
	def access = DbSingleTopicInfoAvailability(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: TopicInfoAvailabilityData) = copy(data = data)
}

