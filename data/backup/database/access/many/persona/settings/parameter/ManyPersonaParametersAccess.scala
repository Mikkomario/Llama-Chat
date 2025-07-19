package vf.llama.database.access.many.persona.settings.parameter

import utopia.bunnymunch.jawn.JsonBunny
import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{FilterableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaParameterDbFactory
import vf.llama.database.storable.persona.PersonaParameterDbModel
import vf.llama.model.stored.persona.PersonaParameter

object ManyPersonaParametersAccess extends ViewFactory[ManyPersonaParametersAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaParametersAccess = 
		_ManyPersonaParametersAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaParametersAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaParametersAccess
}

/**
  * A common trait for access points which target multiple persona parameters at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaParametersAccess 
	extends ManyRowModelAccess[PersonaParameter] with FilterableView[ManyPersonaParametersAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * settings ids of the accessible persona parameters
	  */
	def settingsIds(implicit connection: Connection) = pullColumn(model.settingsId.column)
		.map { v => v.getInt }
	
	/**
	  * keys of the accessible persona parameters
	  */
	def keys(implicit connection: Connection) = pullColumn(model.key.column).flatMap { _.string }
	
	/**
	  * values of the accessible persona parameters
	  */
	def values(implicit connection: Connection) = 
		pullColumn(model.value.column).map { v => v.mapIfNotEmpty { v => JsonBunny.sureMunch(v.getString) } }
	
	/**
	  * Unique ids of the accessible persona parameters
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = PersonaParameterDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaParameterDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): ManyPersonaParametersAccess = ManyPersonaParametersAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param settingsId settings id to target
	  * @return Copy of this access point that only includes persona parameters with the specified settings id
	  */
	def partOfSettings(settingsId: Int) = filter(model.settingsId.column <=> settingsId)
	
	/**
	  * @param settingsIds Targeted settings ids
	  * 
		@return Copy of this access point that only includes persona parameters where settings id is within the
	  *  specified value set
	  */
	def partOfSettingses(settingsIds: IterableOnce[Int]) = 
		filter(model.settingsId.column.in(IntSet.from(settingsIds)))
}

