package vf.llama.database.access.single.persona

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaDbFactory
import vf.llama.database.storable.persona.PersonaDbModel
import vf.llama.model.stored.persona.Persona

/**
  * Used for accessing individual personas
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersona extends SingleRowModelAccess[Persona] with NonDeprecatedView[Persona] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona
	  * @return An access point to that persona
	  */
	def apply(id: Int) = DbSinglePersona(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique personas.
	  * @return An access point to the persona that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniquePersonaAccess(mergeCondition(additionalCondition))
}

