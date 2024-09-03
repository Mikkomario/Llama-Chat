package vf.llama.database.factory.persona

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.persona.StatementLinkedPersonaSettings
import vf.llama.model.stored.persona.{PersonaSettings, PersonaStatementLink}

/**
  * Used for reading statement linked persona settings from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object StatementLinkedPersonaSettingsDbFactory 
	extends MultiCombiningFactory[StatementLinkedPersonaSettings, PersonaSettings, PersonaStatementLink] 
		with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = PersonaStatementLinkDbFactory
	
	override def isAlwaysLinked = false
	
	override def nonDeprecatedCondition = parentFactory.nonDeprecatedCondition
	
	override def parentFactory = PersonaSettingsDbFactory
	
	/**
	  * @param settings settings to wrap
	  * @param links links to attach to this settings
	  */
	override def apply(settings: PersonaSettings, links: Seq[PersonaStatementLink]) = 
		StatementLinkedPersonaSettings(settings, links)
}

