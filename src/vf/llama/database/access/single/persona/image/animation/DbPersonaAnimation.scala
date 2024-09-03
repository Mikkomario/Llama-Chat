package vf.llama.database.access.single.persona.image.animation

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaAnimationDbFactory
import vf.llama.database.storable.persona.PersonaAnimationDbModel
import vf.llama.model.stored.persona.PersonaAnimation

/**
  * Used for accessing individual persona animations
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaAnimation extends SingleRowModelAccess[PersonaAnimation] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaAnimationDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaAnimationDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona animation
	  * @return An access point to that persona animation
	  */
	def apply(id: Int) = DbSinglePersonaAnimation(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona animations.
	  * @return An access point to the persona animation that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniquePersonaAnimationAccess(condition)
}

