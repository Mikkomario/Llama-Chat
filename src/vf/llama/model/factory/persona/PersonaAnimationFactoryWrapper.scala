package vf.llama.model.factory.persona

import utopia.flow.util.Mutate

import scala.concurrent.duration.FiniteDuration

import java.nio.file.Path

/**
  * 
	Common trait for classes that implement PersonaAnimationFactory by wrapping a PersonaAnimationFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaAnimationFactoryWrapper[A <: PersonaAnimationFactory[A], +Repr] 
	extends PersonaAnimationFactory[Repr]
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
	
	override def withDefaultFrameDuration(defaultFrameDuration: FiniteDuration) = 
		mapWrapped { _.withDefaultFrameDuration(defaultFrameDuration) }
	
	override def withRelativeSubDirectory(relativeSubDirectory: Path) = 
		mapWrapped { _.withRelativeSubDirectory(relativeSubDirectory) }
	
	override def withSetId(setId: Int) = mapWrapped { _.withSetId(setId) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

