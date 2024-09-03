package vf.llama.model.combined.persona

import vf.llama.model.stored.persona.{PersonaSettings, PersonaStatementLink}

object StatementLinkedPersonaSettings
{
	// OTHER	--------------------
	
	/**
	  * @param settings settings to wrap
	  * @param links links to attach to this settings
	  * @return Combination of the specified settings and link
	  */
	def apply(settings: PersonaSettings, links: Seq[PersonaStatementLink]): StatementLinkedPersonaSettings = 
		_StatementLinkedPersonaSettings(settings, links)
	
	
	// NESTED	--------------------
	
	/**
	  * @param settings settings to wrap
	  * @param links links to attach to this settings
	  */
	private case class _StatementLinkedPersonaSettings(settings: PersonaSettings, 
		links: Seq[PersonaStatementLink]) 
		extends StatementLinkedPersonaSettings
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: PersonaSettings) = copy(settings = factory)
	}
}

/**
  * Attaches statement links to persona settings
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkedPersonaSettings extends CombinedPersonaSettings[StatementLinkedPersonaSettings]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped persona settings
	  */
	def settings: PersonaSettings
	
	/**
	  * Links that are attached to this settings
	  */
	def links: Seq[PersonaStatementLink]
	
	
	// IMPLEMENTED	--------------------
	
	override def personaSettings = settings
}

