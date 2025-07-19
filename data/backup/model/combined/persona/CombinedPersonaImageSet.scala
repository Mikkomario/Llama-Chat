package vf.llama.model.combined.persona

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.persona.PersonaImageSetFactoryWrapper
import vf.llama.model.partial.persona.PersonaImageSetData
import vf.llama.model.stored.persona.PersonaImageSet

/**
  * Common trait for combinations that add additional data to persona image sets
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedPersonaImageSet[+Repr] 
	extends Extender[PersonaImageSetData] with HasId[Int] 
		with PersonaImageSetFactoryWrapper[PersonaImageSet, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped persona image set
	  */
	def personaImageSet: PersonaImageSet
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this persona image set in the database
	  */
	override def id = personaImageSet.id
	
	override def wrapped = personaImageSet.data
	
	override protected def wrappedFactory = personaImageSet
}

