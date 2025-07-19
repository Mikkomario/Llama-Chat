package vf.llama.model.factory.color

import utopia.flow.util.Mutate
import utopia.paradigm.angular.Angle

/**
  * Common trait for classes that implement ColorFactory by wrapping a ColorFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ColorFactoryWrapper[A <: ColorFactory[A], +Repr] extends ColorFactory[Repr]
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
	
	override def withHue(hue: Angle) = mapWrapped { _.withHue(hue) }
	
	override def withLightness(lightness: Double) = mapWrapped { _.withLightness(lightness) }
	
	override def withPredefined(predefined: Boolean) = mapWrapped { _.withPredefined(predefined) }
	
	override def withSaturation(saturation: Double) = mapWrapped { _.withSaturation(saturation) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

