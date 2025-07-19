package vf.llama.model.partial.meta

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.time.Now

import java.time.Instant

object MetaInfoAvailabilityData extends FromModelFactoryWithSchema[MetaInfoAvailabilityData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("fieldId", IntType, Single("field_id")), 
			PropertyDeclaration("contextId", IntType, Single("context_id")), PropertyDeclaration("created", 
			InstantType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		MetaInfoAvailabilityData(valid("fieldId").getInt, valid("contextId").getInt, 
			valid("created").getInstant)
	
	
	// OTHER	--------------------
	
	/**
	  * Creates a new meta info availability
	  * @param fieldId Id of the meta info -field made available
	  * @param contextId Id of the context where the linked information is made available
	  * @param created Time when this information was made available
	  * @return meta info availability with the specified properties
	  */
	def apply(fieldId: Int, contextId: Int, created: Instant = Now): MetaInfoAvailabilityData = 
		_MetaInfoAvailabilityData(fieldId, contextId, created)
	
	
	// NESTED	--------------------
	
	/**
	  * Concrete implementation of the meta info availability data trait
	  * @param fieldId Id of the meta info -field made available
	  * @param contextId Id of the context where the linked information is made available
	  * @param created Time when this information was made available
	  * @author Mikko Hilpinen
	  * @since 01.09.2024
	  */
	private case class _MetaInfoAvailabilityData(fieldId: Int, contextId: Int, created: Instant = Now) 
		extends MetaInfoAvailabilityData
	{
		// IMPLEMENTED	--------------------
		
		/**
		  * @param fieldId Id of the meta info -field made available
		  * @param contextId Id of the context where the linked information is made available
		  * @param created Time when this information was made available
		  */
		override def copyMetaInfoAvailability(fieldId: Int, contextId: Int, created: Instant = Now) = 
			_MetaInfoAvailabilityData(fieldId, contextId, created)
	}
}

/**
  * Links a meta info field to a context where it is made available to a persona
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityData extends MetaInfoAvailabilityDataLike[MetaInfoAvailabilityData]

