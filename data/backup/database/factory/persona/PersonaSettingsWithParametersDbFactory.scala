package vf.llama.database.factory.persona

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.persona.PersonaSettingsWithParameters
import vf.llama.model.stored.persona.{PersonaParameter, PersonaSettings}

/**
  * Used for reading persona settings with parameters from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaSettingsWithParametersDbFactory 
	extends MultiCombiningFactory[PersonaSettingsWithParameters, PersonaSettings, PersonaParameter] 
		with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = PersonaParameterDbFactory
	
	override def isAlwaysLinked = false
	
	override def nonDeprecatedCondition = parentFactory.nonDeprecatedCondition
	
	override def parentFactory = PersonaSettingsDbFactory
	
	/**
	  * @param settings settings to wrap
	  * @param parameter parameter to attach to this settings
	  */
	override def apply(settings: PersonaSettings, parameter: Seq[PersonaParameter]) = 
		PersonaSettingsWithParameters(settings, parameter)
}

