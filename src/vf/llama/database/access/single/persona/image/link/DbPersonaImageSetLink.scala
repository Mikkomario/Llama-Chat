package vf.llama.database.access.single.persona.image.link

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaImageSetLinkDbFactory
import vf.llama.database.storable.persona.PersonaImageSetLinkDbModel
import vf.llama.model.stored.persona.PersonaImageSetLink

/**
  * Used for accessing individual persona image set links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaImageSetLink 
	extends SingleRowModelAccess[PersonaImageSetLink] with NonDeprecatedView[PersonaImageSetLink] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaImageSetLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaImageSetLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona image set link
	  * @return An access point to that persona image set link
	  */
	def apply(id: Int) = DbSinglePersonaImageSetLink(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona image set links.
	  * @return An access point to the persona image set link that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniquePersonaImageSetLinkAccess(mergeCondition(additionalCondition))
}

