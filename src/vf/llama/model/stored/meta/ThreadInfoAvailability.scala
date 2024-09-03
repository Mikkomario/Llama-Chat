package vf.llama.model.stored.meta

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.meta.availability.thread.DbSingleThreadInfoAvailability
import vf.llama.model.factory.meta.ThreadInfoAvailabilityFactoryWrapper
import vf.llama.model.partial.meta.{MetaInfoAvailabilityData, ThreadInfoAvailabilityData}

object ThreadInfoAvailability 
	extends StoredFromModelFactory[ThreadInfoAvailabilityData, ThreadInfoAvailability]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = ThreadInfoAvailabilityData
	
	override protected def complete(model: AnyModel, data: ThreadInfoAvailabilityData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a thread info availability that has already been stored in the database
  * @param id id of this thread info availability in the database
  * @param data Wrapped thread info availability data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ThreadInfoAvailability(id: Int, data: ThreadInfoAvailabilityData) 
	extends ThreadInfoAvailabilityFactoryWrapper[ThreadInfoAvailabilityData, ThreadInfoAvailability] 
		with MetaInfoAvailabilityData 
		with StoredMetaInfoAvailabilityLike[ThreadInfoAvailabilityData, ThreadInfoAvailability]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this thread info availability in the database
	  */
	def access = DbSingleThreadInfoAvailability(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: ThreadInfoAvailabilityData) = copy(data = data)
}

