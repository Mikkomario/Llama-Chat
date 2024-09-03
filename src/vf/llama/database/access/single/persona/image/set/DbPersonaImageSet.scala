package vf.llama.database.access.single.persona.image.set

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaImageSetDbFactory
import vf.llama.database.storable.persona.PersonaImageSetDbModel
import vf.llama.model.stored.persona.PersonaImageSet

/**
  * Used for accessing individual persona image sets
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaImageSet 
	extends SingleRowModelAccess[PersonaImageSet] with NonDeprecatedView[PersonaImageSet] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaImageSetDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaImageSetDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona image set
	  * @return An access point to that persona image set
	  */
	def apply(id: Int) = DbSinglePersonaImageSet(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona image sets.
	  * @return An access point to the persona image set that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniquePersonaImageSetAccess(mergeCondition(additionalCondition))
}

