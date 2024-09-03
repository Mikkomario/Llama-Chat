package vf.llama.model.stored.meta

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.model.partial.meta.MetaInfoAvailabilityData

object MetaInfoAvailability extends StoredFromModelFactory[MetaInfoAvailabilityData, MetaInfoAvailability]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = MetaInfoAvailabilityData
	
	override protected def complete(model: AnyModel, data: MetaInfoAvailabilityData) = 
		model("id").tryInt.map { apply(_, data) }
	
	
	// OTHER	--------------------
	
	/**
	  * Creates a new meta info availability
	  * @param id id of this meta info availability in the database
	  * @param data Wrapped meta info availability data
	  * @return meta info availability with the specified id and wrapped data
	  */
	def apply(id: Int, data: MetaInfoAvailabilityData): MetaInfoAvailability = _MetaInfoAvailability(id, data)
	
	
	// NESTED	--------------------
	
	/**
	  * Concrete implementation of the meta info availability trait
	  * @param id id of this meta info availability in the database
	  * @param data Wrapped meta info availability data
	  * @author Mikko Hilpinen
	  * @since 01.09.2024
	  */
	private case class _MetaInfoAvailability(id: Int, 
		data: MetaInfoAvailabilityData) extends MetaInfoAvailability
	{
		// IMPLEMENTED	--------------------
		
		override def withId(id: Int) = copy(id = id)
		
		override protected def wrap(data: MetaInfoAvailabilityData) = copy(data = data)
	}
}

/**
  * Represents a meta info availability that has already been stored in the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailability 
	extends StoredMetaInfoAvailabilityLike[MetaInfoAvailabilityData, MetaInfoAvailability] 
		with MetaInfoAvailabilityData

