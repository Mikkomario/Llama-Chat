package vf.llama.model.partial.meta

import vf.llama.model.factory.meta.MetaInfoAvailabilityFactory

import java.time.Instant

/**
  * Common trait for classes which provide read and copy access to meta info availability properties
  * @tparam Repr Implementing data class or data wrapper class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityDataLike[+Repr] 
	extends HasMetaInfoAvailabilityProps with MetaInfoAvailabilityFactory[Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Builds a modified copy of this meta info availability
	  * @param fieldId New field id to assign. Default = current value.
	  * @param contextId New context id to assign. Default = current value.
	  * @param created New created to assign. Default = current value.
	  * @return A copy of this meta info availability with the specified properties
	  */
	def copyMetaInfoAvailability(fieldId: Int = fieldId, contextId: Int = contextId, 
		created: Instant = created): Repr
	
	
	// IMPLEMENTED	--------------------
	
	override def withContextId(contextId: Int) = copyMetaInfoAvailability(contextId = contextId)
	
	override def withCreated(created: Instant) = copyMetaInfoAvailability(created = created)
	
	override def withFieldId(fieldId: Int) = copyMetaInfoAvailability(fieldId = fieldId)
}

