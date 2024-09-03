package vf.llama.database.access.many.persona.settings

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaSettingsDbFactory
import vf.llama.model.stored.persona.PersonaSettings

object ManyPersonaSettingsAccess extends ViewFactory[ManyPersonaSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaSettingsAccess = 
		_ManyPersonaSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaSettingsAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaSettingsAccess
}

/**
  * A common trait for access points which target multiple persona settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaSettingsAccess 
	extends ManyPersonaSettingsAccessLike[PersonaSettings, ManyPersonaSettingsAccess] 
		with ManyRowModelAccess[PersonaSettings] 
		with ChronoRowFactoryView[PersonaSettings, ManyPersonaSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaSettingsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonaSettingsAccess = ManyPersonaSettingsAccess(condition)
}

