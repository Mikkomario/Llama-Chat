package vf.llama.model.factory.persona

import utopia.flow.util.Mutate

import java.time.Instant

/**
  * Common trait for classes that implement PersonaImageSetLinkFactory by wrapping
  *  a PersonaImageSetLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaImageSetLinkFactoryWrapper[A <: PersonaImageSetLinkFactory[A], +Repr] 
	extends PersonaImageSetLinkFactory[Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * The factory wrapped by this instance
	  */
	protected def wrappedFactory: A
	
	/**
	  * Mutates this item by wrapping a mutated instance
	  * @param factory The new factory instance to wrap
	  * @return Copy of this item with the specified wrapped factory
	  */
	protected def wrap(factory: A): Repr
	
	
	// IMPLEMENTED	--------------------
	
	override def withCreated(created: Instant) = mapWrapped { _.withCreated(created) }
	
	override def withImageSetId(imageSetId: Int) = mapWrapped { _.withImageSetId(imageSetId) }
	
	override def withPersonaId(personaId: Int) = mapWrapped { _.withPersonaId(personaId) }
	
	override def withRemovedAfter(removedAfter: Instant) = mapWrapped { _.withRemovedAfter(removedAfter) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

