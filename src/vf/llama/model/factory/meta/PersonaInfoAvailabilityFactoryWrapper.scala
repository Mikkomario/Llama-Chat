package vf.llama.model.factory.meta

/**
  * Common trait for classes that implement PersonaInfoAvailabilityFactory by wrapping
  *  a PersonaInfoAvailabilityFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaInfoAvailabilityFactoryWrapper[A <: PersonaInfoAvailabilityFactory[A], +Repr] 
	extends PersonaInfoAvailabilityFactory[Repr] with MetaInfoAvailabilityFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withPersonaId(personaId: Int) = withContextId(personaId)
}

