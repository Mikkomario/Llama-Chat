package vf.llama.model.factory.persona

import utopia.flow.generic.model.immutable.Value

/**
  * Common trait for persona parameter-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaParameterFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param key New key to assign
	  * @return Copy of this item with the specified key
	  */
	def withKey(key: String): A
	
	/**
	  * @param settingsId New settings id to assign
	  * @return Copy of this item with the specified settings id
	  */
	def withSettingsId(settingsId: Int): A
	
	/**
	  * @param value New value to assign
	  * @return Copy of this item with the specified value
	  */
	def withValue(value: Value): A
}

