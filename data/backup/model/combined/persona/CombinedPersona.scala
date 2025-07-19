package vf.llama.model.combined.persona

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.persona.PersonaFactoryWrapper
import vf.llama.model.partial.persona.PersonaData
import vf.llama.model.stored.persona.Persona

/**
  * Common trait for combinations that add additional data to personas
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedPersona[+Repr] 
	extends Extender[PersonaData] with HasId[Int] with PersonaFactoryWrapper[Persona, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped persona
	  */
	def persona: Persona
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this persona in the database
	  */
	override def id = persona.id
	
	override def wrapped = persona.data
	
	override protected def wrappedFactory = persona
}

