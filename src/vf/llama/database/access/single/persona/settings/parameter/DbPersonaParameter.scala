package vf.llama.database.access.single.persona.settings.parameter

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaParameterDbFactory
import vf.llama.database.storable.persona.PersonaParameterDbModel
import vf.llama.model.stored.persona.PersonaParameter

/**
  * Used for accessing individual persona parameters
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaParameter extends SingleRowModelAccess[PersonaParameter] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaParameterDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaParameterDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona parameter
	  * @return An access point to that persona parameter
	  */
	def apply(id: Int) = DbSinglePersonaParameter(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona parameters.
	  * @return An access point to the persona parameter that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniquePersonaParameterAccess(condition)
}

