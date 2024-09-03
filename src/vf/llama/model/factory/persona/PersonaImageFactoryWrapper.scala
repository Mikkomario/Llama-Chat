package vf.llama.model.factory.persona

import utopia.flow.util.Mutate

import scala.concurrent.duration.FiniteDuration

/**
  * Common trait for classes that implement PersonaImageFactory by wrapping a PersonaImageFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaImageFactoryWrapper[A <: PersonaImageFactory[A], +Repr] extends PersonaImageFactory[Repr]
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
	
	override def withAnimationId(animationId: Int) = mapWrapped { _.withAnimationId(animationId) }
	
	override def withCustomDuration(customDuration: FiniteDuration) = 
		mapWrapped { _.withCustomDuration(customDuration) }
	
	override def withFileName(fileName: String) = mapWrapped { _.withFileName(fileName) }
	
	override def withOrderIndex(orderIndex: Int) = mapWrapped { _.withOrderIndex(orderIndex) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

