package vf.llama.model.combined.persona

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.persona.PersonaSettingsFactoryWrapper
import vf.llama.model.partial.persona.PersonaSettingsData
import vf.llama.model.stored.persona.PersonaSettings

/**
  * Common trait for combinations that add additional data to persona settings
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedPersonaSettings[+Repr] 
	extends Extender[PersonaSettingsData] with HasId[Int] 
		with PersonaSettingsFactoryWrapper[PersonaSettings, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped persona settings
	  */
	def personaSettings: PersonaSettings
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this persona settings in the database
	  */
	override def id = personaSettings.id
	
	override def wrapped = personaSettings.data
	
	override protected def wrappedFactory = personaSettings
}

