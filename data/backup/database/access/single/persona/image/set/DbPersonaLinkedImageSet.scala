package vf.llama.database.access.single.persona.image.set

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaLinkedImageSetDbFactory
import vf.llama.database.storable.persona.{PersonaImageSetDbModel, PersonaImageSetLinkDbModel}
import vf.llama.model.combined.persona.PersonaLinkedImageSet

/**
  * Used for accessing individual persona linked image sets
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaLinkedImageSet 
	extends SingleRowModelAccess[PersonaLinkedImageSet] with NonDeprecatedView[PersonaLinkedImageSet] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = PersonaImageSetLinkDbModel
	
	/**
	  * A database model (factory) used for interacting with linked image sets
	  */
	private def model = PersonaImageSetDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaLinkedImageSetDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona linked image set
	  * @return An access point to that persona linked image set
	  */
	def apply(id: Int) = DbSinglePersonaLinkedImageSet(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona linked image sets.
	  * @return An access point to the persona linked image set that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniquePersonaLinkedImageSetAccess(mergeCondition(additionalCondition))
}

