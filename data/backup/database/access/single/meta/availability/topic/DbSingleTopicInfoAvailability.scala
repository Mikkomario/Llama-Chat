package vf.llama.database.access.single.meta.availability.topic

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.meta.TopicInfoAvailability

/**
  * An access point to individual topic info availabilities, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleTopicInfoAvailability(id: Int) 
	extends UniqueTopicInfoAvailabilityAccess with SingleIntIdModelAccess[TopicInfoAvailability]

