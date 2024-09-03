package vf.llama.model.partial.meta

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.time.Now
import vf.llama.model.factory.meta.ThreadInfoAvailabilityFactory

import java.time.Instant

object ThreadInfoAvailabilityData extends FromModelFactoryWithSchema[ThreadInfoAvailabilityData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("threadId", IntType, Vector("contextId", "context_id", 
			"thread_id")), PropertyDeclaration("fieldId", IntType, Single("field_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		ThreadInfoAvailabilityData(valid("threadId").getInt, valid("fieldId").getInt, 
			valid("created").getInstant)
}

/**
  * A link that makes a meta info field available within a specific thread
  * @param threadId Id of the thread where the linked information is made available
  * @param fieldId Id of the meta info -field made available
  * @param created Time when this information was made available
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ThreadInfoAvailabilityData(threadId: Int, fieldId: Int, created: Instant = Now) 
	extends ThreadInfoAvailabilityFactory[ThreadInfoAvailabilityData] with MetaInfoAvailabilityData 
		with MetaInfoAvailabilityDataLike[ThreadInfoAvailabilityData]
{
	// IMPLEMENTED	--------------------
	
	override def contextId = threadId
	
	override def copyMetaInfoAvailability(fieldId: Int, contextId: Int, created: Instant) = 
		copy(threadId = contextId, fieldId = fieldId, created = created)
	
	override def withThreadId(threadId: Int) = copy(threadId = threadId)
}

