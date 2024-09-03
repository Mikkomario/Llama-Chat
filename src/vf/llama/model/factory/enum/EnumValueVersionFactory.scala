package vf.llama.model.factory.`enum`

import java.time.Instant

/**
  * Common trait for enum value version-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait EnumValueVersionFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param colorId New color id to assign
	  * @return Copy of this item with the specified color id
	  */
	def withColorId(colorId: Int): A
	
	/**
	  * @param created New created to assign
	  * @return Copy of this item with the specified created
	  */
	def withCreated(created: Instant): A
	
	/**
	  * @param deprecatedAfter New deprecated after to assign
	  * @return Copy of this item with the specified deprecated after
	  */
	def withDeprecatedAfter(deprecatedAfter: Instant): A
	
	/**
	  * @param enumValueId New enum value id to assign
	  * @return Copy of this item with the specified enum value id
	  */
	def withEnumValueId(enumValueId: Int): A
	
	/**
	  * @param name New name to assign
	  * @return Copy of this item with the specified name
	  */
	def withName(name: String): A
}

