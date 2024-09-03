package vf.llama.model.factory.persona

import utopia.flow.generic.model.immutable.Value
import utopia.flow.util.Mutate

/**
  * 
	Common trait for classes that implement PersonaParameterFactory by wrapping a PersonaParameterFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaParameterFactoryWrapper[A <: PersonaParameterFactory[A], +Repr] 
	extends PersonaParameterFactory[Repr]
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
	
	override def withKey(key: String) = mapWrapped { _.withKey(key) }
	
	override def withSettingsId(settingsId: Int) = mapWrapped { _.withSettingsId(settingsId) }
	
	override def withValue(value: Value) = mapWrapped { _.withValue(value) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

