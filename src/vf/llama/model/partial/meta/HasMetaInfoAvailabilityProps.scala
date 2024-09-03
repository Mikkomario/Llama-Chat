package vf.llama.model.partial.meta

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Model
import utopia.flow.generic.model.template.ModelConvertible

import java.time.Instant

/**
  * Common trait for classes which provide access to meta info availability properties
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait HasMetaInfoAvailabilityProps extends ModelConvertible
{
	// ABSTRACT	--------------------
	
	/**
	  * Id of the meta info -field made available
	  */
	def fieldId: Int
	
	/**
	  * Id of the context where the linked information is made available
	  */
	def contextId: Int
	
	/**
	  * Time when this information was made available
	  */
	def created: Instant
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("fieldId" -> fieldId, "contextId" -> contextId, "created" -> created))
}

