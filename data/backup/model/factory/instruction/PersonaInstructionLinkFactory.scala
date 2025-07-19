package vf.llama.model.factory.instruction

/**
  * Common trait for persona instruction link-related factories which allow construction 
  * with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaInstructionLinkFactory[+A] extends InstructionTargetingLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param personaId New persona id to assign
	  * @return Copy of this item with the specified persona id
	  */
	def withPersonaId(personaId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withTargetId(targetId: Int) = withPersonaId(targetId)
}

