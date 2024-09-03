package vf.llama.database.factory.persona

import utopia.vault.nosql.factory.row.linked.CombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.persona.PersonaWithSettings
import vf.llama.model.stored.persona.{Persona, PersonaSettings}

/**
  * Used for reading personas with settings from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaWithSettingsDbFactory 
	extends CombiningFactory[PersonaWithSettings, Persona, PersonaSettings] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = PersonaSettingsDbFactory
	
	override def nonDeprecatedCondition = 
		parentFactory.nonDeprecatedCondition && childFactory.nonDeprecatedCondition
	
	override def parentFactory = PersonaDbFactory
	
	/**
	  * @param persona persona to wrap
	  * @param settings settings to attach to this persona
	  */
	override def apply(persona: Persona, settings: PersonaSettings) = PersonaWithSettings(persona, settings)
}

