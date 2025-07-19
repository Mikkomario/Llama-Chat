package vf.llama.model.combined.persona

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.persona.PersonaAnimationFactoryWrapper
import vf.llama.model.partial.persona.PersonaAnimationData
import vf.llama.model.stored.persona.PersonaAnimation

/**
  * Common trait for combinations that add additional data to persona animations
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedPersonaAnimation[+Repr] 
	extends Extender[PersonaAnimationData] with HasId[Int] 
		with PersonaAnimationFactoryWrapper[PersonaAnimation, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped persona animation
	  */
	def personaAnimation: PersonaAnimation
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this persona animation in the database
	  */
	override def id = personaAnimation.id
	
	override def wrapped = personaAnimation.data
	
	override protected def wrappedFactory = personaAnimation
}

