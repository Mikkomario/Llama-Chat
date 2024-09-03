package vf.llama.model.factory.meta

import java.time.Instant

/**
  * Common trait for meta info availability-related factories which allow construction 
	with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param contextId New context id to assign
	  * @return Copy of this item with the specified context id
	  */
	def withContextId(contextId: Int): A
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param fieldId New field id to assign
	  * @return Copy of this item with the specified field id
	  */
	def withFieldId(fieldId: Int): A
}

