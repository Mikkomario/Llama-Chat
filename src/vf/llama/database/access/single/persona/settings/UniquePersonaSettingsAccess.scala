package vf.llama.database.access.single.persona.settings

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaSettingsDbFactory
import vf.llama.model.stored.persona.PersonaSettings

object UniquePersonaSettingsAccess extends ViewFactory[UniquePersonaSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaSettingsAccess = 
		_UniquePersonaSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaSettingsAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaSettingsAccess
}

/**
  * A common trait for access points that return individual and distinct persona settings.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaSettingsAccess 
	extends UniquePersonaSettingsAccessLike[PersonaSettings, UniquePersonaSettingsAccess] 
		with SingleChronoRowModelAccess[PersonaSettings, UniquePersonaSettingsAccess] 
		with NullDeprecatableView[UniquePersonaSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaSettingsDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): UniquePersonaSettingsAccess = UniquePersonaSettingsAccess(condition)
}

