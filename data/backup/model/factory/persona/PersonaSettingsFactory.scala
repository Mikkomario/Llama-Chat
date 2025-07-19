package vf.llama.model.factory.persona

import java.time.Instant

/**
  * Common trait for persona settings-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaSettingsFactory[+A]
{
	// ABSTRACT	--------------------
	
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
	  * @param llmVariantId New llm variant id to assign
	  * @return Copy of this item with the specified llm variant id
	  */
	def withLlmVariantId(llmVariantId: Int): A
	
	/**
	  * @param name New name to assign
	  * @return Copy of this item with the specified name
	  */
	def withName(name: String): A
	
	/**
	  * @param personaId New persona id to assign
	  * @return Copy of this item with the specified persona id
	  */
	def withPersonaId(personaId: Int): A
}

