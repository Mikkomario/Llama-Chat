package vf.llama.database.access.single.persona.settings.parameter

import utopia.bunnymunch.jawn.JsonBunny
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{FilterableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaParameterDbFactory
import vf.llama.database.storable.persona.PersonaParameterDbModel
import vf.llama.model.stored.persona.PersonaParameter

object UniquePersonaParameterAccess extends ViewFactory[UniquePersonaParameterAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniquePersonaParameterAccess = 
		_UniquePersonaParameterAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniquePersonaParameterAccess(override val accessCondition: Option[Condition]) 
		extends UniquePersonaParameterAccess
}

/**
  * A common trait for access points that return individual and distinct persona parameters.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniquePersonaParameterAccess 
	extends SingleRowModelAccess[PersonaParameter] 
		with DistinctModelAccess[PersonaParameter, Option[PersonaParameter], Value] 
		with FilterableView[UniquePersonaParameterAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the persona settings this parameter assignment is part of. 
	  * None if no persona parameter (or value) was found.
	  */
	def settingsId(implicit connection: Connection) = pullColumn(model.settingsId.column).int
	
	/**
	  * Name / key of the adjusted parameter. 
	  * None if no persona parameter (or value) was found.
	  */
	def key(implicit connection: Connection) = pullColumn(model.key.column).getString
	
	/**
	  * Value assigned to this parameter. 
	  * None if no persona parameter (or value) was found.
	  */
	def value(implicit connection: Connection) = 
		pullColumn(model.value.column).mapIfNotEmpty { v => JsonBunny.sureMunch(v.getString) }
	
	/**
	  * Unique id of the accessible persona parameter. None if no persona parameter was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaParameterDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaParameterDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniquePersonaParameterAccess = 
		UniquePersonaParameterAccess(condition)
}

