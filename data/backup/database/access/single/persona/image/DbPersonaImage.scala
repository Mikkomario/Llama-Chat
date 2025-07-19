package vf.llama.database.access.single.persona.image

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaImageDbFactory
import vf.llama.database.storable.persona.PersonaImageDbModel
import vf.llama.model.stored.persona.PersonaImage

/**
  * Used for accessing individual persona images
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaImage extends SingleRowModelAccess[PersonaImage] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaImageDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaImageDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona image
	  * @return An access point to that persona image
	  */
	def apply(id: Int) = DbSinglePersonaImage(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona images.
	  * @return An access point to the persona image that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniquePersonaImageAccess(condition)
}

