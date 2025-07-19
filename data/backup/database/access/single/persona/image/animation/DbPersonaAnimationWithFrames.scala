package vf.llama.database.access.single.persona.image.animation

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaAnimationWithFramesDbFactory
import vf.llama.database.storable.persona.{PersonaAnimationDbModel, PersonaImageDbModel}
import vf.llama.model.combined.persona.PersonaAnimationWithFrames

/**
  * Used for accessing individual persona animations with image frames
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaAnimationWithFrames 
	extends SingleModelAccess[PersonaAnimationWithFrames] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked frame
	  */
	protected def frameModel = PersonaImageDbModel
	
	/**
	  * A database model (factory) used for interacting with linked animations
	  */
	private def model = PersonaAnimationDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaAnimationWithFramesDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona animation with frames
	  * @return An access point to that persona animation with frames
	  */
	def apply(id: Int) = DbSinglePersonaAnimationWithFrames(id)
	
	/**
	  * 
		@param condition Filter condition to apply in addition to this root view's condition. Should yield unique persona animations 
	  * with image frames.
	  * @return An access point to the persona animation with frames that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniquePersonaAnimationWithFramesAccess(condition)
}

