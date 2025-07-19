package vf.llama.model.factory.meta

/**
  * Common trait for persona info availability-related factories which allow construction 
  * with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaInfoAvailabilityFactory[+A] extends MetaInfoAvailabilityFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param personaId New persona id to assign
	  * @return Copy of this item with the specified persona id
	  */
	def withPersonaId(personaId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withContextId(contextId: Int) = withPersonaId(contextId)
}

