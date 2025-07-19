package vf.llama.database.access.many.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaWithSettingsDbFactory
import vf.llama.database.storable.persona.PersonaSettingsDbModel
import vf.llama.model.combined.persona.PersonaWithSettings

import java.time.Instant

object ManyPersonasWithSettingsAccess extends ViewFactory[ManyPersonasWithSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonasWithSettingsAccess = 
		_ManyPersonasWithSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonasWithSettingsAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonasWithSettingsAccess
}

/**
  * A common trait for access points that return multiple personas with settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyPersonasWithSettingsAccess 
	extends ManyPersonasAccessLike[PersonaWithSettings, ManyPersonasWithSettingsAccess] 
		with ManyRowModelAccess[PersonaWithSettings]
{
	// COMPUTED	--------------------
	
	/**
	  * persona ids of the accessible persona settings
	  */
	def settingsPersonaIds(implicit connection: Connection) = 
		pullColumn(settingsModel.personaId.column).map { v => v.getInt }
	
	/**
	  * llm variant ids of the accessible persona settings
	  */
	def settingsLlmVariantIds(implicit connection: Connection) = 
		pullColumn(settingsModel.llmVariantId.column).map { v => v.getInt }
	
	/**
	  * names of the accessible persona settings
	  */
	def settingsNames(implicit connection: Connection) = 
		pullColumn(settingsModel.name.column).flatMap { _.string }
	
	/**
	  * creation times of the accessible persona settings
	  */
	def settingsCreationTimes(implicit connection: Connection) = 
		pullColumn(settingsModel.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible persona settings
	  */
	def settingsDeprecationTimes(implicit connection: Connection) = 
		pullColumn(settingsModel.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Model (factory) used for interacting the persona settings associated with this persona with settings
	  */
	protected def settingsModel = PersonaSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaWithSettingsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonasWithSettingsAccess = 
		ManyPersonasWithSettingsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted persona settings
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any persona settings was affected
	  */
	def settingsDeprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(settingsModel.deprecatedAfter.column, newDeprecatedAfter)
}

