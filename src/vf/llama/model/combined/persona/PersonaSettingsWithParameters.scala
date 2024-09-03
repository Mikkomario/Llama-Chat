package vf.llama.model.combined.persona

import vf.llama.model.stored.persona.{PersonaParameter, PersonaSettings}

object PersonaSettingsWithParameters
{
	// OTHER	--------------------
	
	/**
	  * @param settings settings to wrap
	  * @param parameter parameter to attach to this settings
	  * @return Combination of the specified settings and parameter
	  */
	def apply(settings: PersonaSettings, parameter: Seq[PersonaParameter]): PersonaSettingsWithParameters = 
		_PersonaSettingsWithParameters(settings, parameter)
	
	
	// NESTED	--------------------
	
	/**
	  * @param settings settings to wrap
	  * @param parameter parameter to attach to this settings
	  */
	private case class _PersonaSettingsWithParameters(settings: PersonaSettings, 
		parameter: Seq[PersonaParameter]) 
		extends PersonaSettingsWithParameters
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: PersonaSettings) = copy(settings = factory)
	}
}

/**
  * Attaches parameter assignments to a persona settings instance
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaSettingsWithParameters extends CombinedPersonaSettings[PersonaSettingsWithParameters]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped persona settings
	  */
	def settings: PersonaSettings
	
	/**
	  * Parameter that are attached to this settings
	  */
	def parameter: Seq[PersonaParameter]
	
	
	// IMPLEMENTED	--------------------
	
	override def personaSettings = settings
}

