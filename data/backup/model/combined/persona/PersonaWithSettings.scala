package vf.llama.model.combined.persona

import vf.llama.model.stored.persona.{Persona, PersonaSettings}

object PersonaWithSettings
{
	// OTHER	--------------------
	
	/**
	  * @param persona persona to wrap
	  * @param settings settings to attach to this persona
	  * @return Combination of the specified persona and settings
	  */
	def apply(persona: Persona, settings: PersonaSettings): PersonaWithSettings = 
		_PersonaWithSettings(persona, settings)
	
	
	// NESTED	--------------------
	
	/**
	  * @param persona persona to wrap
	  * @param settings settings to attach to this persona
	  */
	private case class _PersonaWithSettings(persona: Persona, settings: PersonaSettings) 
		extends PersonaWithSettings
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: Persona) = copy(persona = factory)
	}
}

/**
  * Attaches settings to a persona. Doesn't include descriptions.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaWithSettings extends CombinedPersona[PersonaWithSettings]
{
	// ABSTRACT	--------------------
	
	/**
	  * The settings that is attached to this persona
	  */
	def settings: PersonaSettings
}

