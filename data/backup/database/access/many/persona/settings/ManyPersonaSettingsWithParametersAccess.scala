package vf.llama.database.access.many.persona.settings

import utopia.bunnymunch.jawn.JsonBunny
import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaSettingsWithParametersDbFactory
import vf.llama.database.storable.persona.PersonaParameterDbModel
import vf.llama.model.combined.persona.PersonaSettingsWithParameters

object ManyPersonaSettingsWithParametersAccess extends ViewFactory[ManyPersonaSettingsWithParametersAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaSettingsWithParametersAccess = 
		_ManyPersonaSettingsWithParametersAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaSettingsWithParametersAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManyPersonaSettingsWithParametersAccess
}

/**
  * A common trait for access points that return multiple persona settings with parameters at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyPersonaSettingsWithParametersAccess 
	extends ManyPersonaSettingsAccessLike[PersonaSettingsWithParameters, 
		ManyPersonaSettingsWithParametersAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * settings ids of the accessible persona parameters
	  */
	def parameterSettingsIds(implicit connection: Connection) = 
		pullColumn(parameterModel.settingsId.column).map { v => v.getInt }
	
	/**
	  * keys of the accessible persona parameters
	  */
	def parameterKeys(implicit connection: Connection) = 
		pullColumn(parameterModel.key.column).flatMap { _.string }
	
	/**
	  * values of the accessible persona parameters
	  */
	def parameterValues(implicit connection: Connection) = 
		pullColumn(parameterModel.value.column)
			.map { v => v.mapIfNotEmpty { v => JsonBunny.sureMunch(v.getString) } }
	
	/**
	  * Model (factory) used for interacting the persona parameters associated with this persona settings 
	  * with parameters
	  */
	protected def parameterModel = PersonaParameterDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaSettingsWithParametersDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonaSettingsWithParametersAccess = 
		ManyPersonaSettingsWithParametersAccess(condition)
}

