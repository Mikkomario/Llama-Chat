package vf.llama.database.access.single.persona

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaWithSettingsDbFactory
import vf.llama.database.storable.persona.PersonaSettingsDbModel
import vf.llama.model.combined.persona.PersonaWithSettings

import java.time.Instant

object UniquePersonaWithSettingsAccess extends ViewFactory[UniquePersonaWithSettingsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaWithSettingsAccess = 
		_UniquePersonaWithSettingsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaWithSettingsAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaWithSettingsAccess
}

/**
  * A common trait for access points that return distinct personas with settings
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaWithSettingsAccess 
	extends UniquePersonaAccessLike[PersonaWithSettings, UniquePersonaWithSettingsAccess] 
		with SingleRowModelAccess[PersonaWithSettings] 
		with NullDeprecatableView[UniquePersonaWithSettingsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the persona which these settings describe. 
	  * None if no persona settings (or value) was found.
	  */
	def settingsPersonaId(implicit connection: Connection) = pullColumn(settingsModel.personaId.column).int
	
	/**
	  * Id of the LLM variant this persona wraps. 
	  * None if no persona settings (or value) was found.
	  */
	def settingsLlmVariantId(implicit connection: Connection) = pullColumn(settingsModel
		.llmVariantId.column).int
	
	/**
	  * Name assigned for this persona. 
	  * None if no persona settings (or value) was found.
	  */
	def settingsName(implicit connection: Connection) = pullColumn(settingsModel.name.column).getString
	
	/**
	  * Time when this persona settings was added to the database. 
	  * None if no persona settings (or value) was found.
	  */
	def settingsCreated(implicit connection: Connection) = pullColumn(settingsModel.created.column).instant
	
	/**
	  * Time when these settings were replaced with a new version. 
	  * None if no persona settings (or value) was found.
	  */
	def settingsDeprecatedAfter(implicit connection: Connection) = 
		pullColumn(settingsModel.deprecatedAfter.column).instant
	
	/**
	  * A database model (factory) used for interacting with the linked settings
	  */
	protected def settingsModel = PersonaSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaWithSettingsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaWithSettingsAccess = 
		UniquePersonaWithSettingsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted persona settings
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any persona settings was affected
	  */
	def settingsDeprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(settingsModel.deprecatedAfter.column, newDeprecatedAfter)
}

