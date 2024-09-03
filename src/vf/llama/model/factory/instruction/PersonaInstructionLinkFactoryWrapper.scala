package vf.llama.model.factory.instruction

/**
  * Common trait for classes that implement PersonaInstructionLinkFactory by wrapping
  *  a PersonaInstructionLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaInstructionLinkFactoryWrapper[A <: PersonaInstructionLinkFactory[A], +Repr] 
	extends PersonaInstructionLinkFactory[Repr] with InstructionTargetingLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withPersonaId(personaId: Int) = withTargetId(personaId)
}

