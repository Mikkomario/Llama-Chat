package vf.llama.database.access.single.persona.settings

import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaSettingsWithParametersDbFactory
import vf.llama.database.storable.persona.PersonaParameterDbModel
import vf.llama.model.combined.persona.PersonaSettingsWithParameters

object UniquePersonaSettingsWithParametersAccess 
	extends ViewFactory[UniquePersonaSettingsWithParametersAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaSettingsWithParametersAccess = 
		_UniquePersonaSettingsWithParametersAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaSettingsWithParametersAccess(override
		 val accessCondition: Option[Condition]) 
		extends UniquePersonaSettingsWithParametersAccess
}

/**
  * A common trait for access points that return distinct persona settings with parameters
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaSettingsWithParametersAccess 
	extends UniquePersonaSettingsAccessLike[PersonaSettingsWithParameters, UniquePersonaSettingsWithParametersAccess] 
		with NullDeprecatableView[UniquePersonaSettingsWithParametersAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked parameter
	  */
	protected def parameterModel = PersonaParameterDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaSettingsWithParametersDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaSettingsWithParametersAccess = 
		UniquePersonaSettingsWithParametersAccess(condition)
}

