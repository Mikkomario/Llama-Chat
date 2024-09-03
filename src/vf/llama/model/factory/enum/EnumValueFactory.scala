package vf.llama.model.factory.`enum`

import java.time.Instant

/**
  * Common trait for enum value-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait EnumValueFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param archivedAfter New archived after to assign
	  * @return Copy of this item with the specified archived after
	  */
	def withArchivedAfter(archivedAfter: Instant): A
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param enumId New enum id to assign
	  * @return Copy of this item with the specified enum id
	  */
	def withEnumId(enumId: Int): A
}

