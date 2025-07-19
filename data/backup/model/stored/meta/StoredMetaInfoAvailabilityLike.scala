package vf.llama.model.stored.meta

import utopia.vault.model.template.{FromIdFactory, Stored}
import vf.llama.model.factory.meta.MetaInfoAvailabilityFactoryWrapper
import vf.llama.model.partial.meta.MetaInfoAvailabilityDataLike

import java.time.Instant

/**
  * Common trait for meta info availabilities which have been stored in the database
  * @tparam Data Type of the wrapped data
  * @tparam Repr Implementing type
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StoredMetaInfoAvailabilityLike[Data <: MetaInfoAvailabilityDataLike[Data], +Repr] 
	extends Stored[Data, Int] with FromIdFactory[Int, Repr] 
		with MetaInfoAvailabilityFactoryWrapper[Data, Repr] with MetaInfoAvailabilityDataLike[Repr]
{
	// IMPLEMENTED	--------------------
	
	override def contextId = data.contextId
	
	override def created = data.created
	
	override def fieldId = data.fieldId
	
	override protected def wrappedFactory = data
	
	override def copyMetaInfoAvailability(fieldId: Int, contextId: Int, created: Instant) = 
		wrap(data.copyMetaInfoAvailability(fieldId, contextId, created))
}

