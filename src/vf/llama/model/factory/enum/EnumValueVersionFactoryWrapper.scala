package vf.llama.model.factory.`enum`

import utopia.flow.util.Mutate

import java.time.Instant

/**
  * 
	Common trait for classes that implement EnumValueVersionFactory by wrapping a EnumValueVersionFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait EnumValueVersionFactoryWrapper[A <: EnumValueVersionFactory[A], +Repr] 
	extends EnumValueVersionFactory[Repr]
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
	
	override def withColorId(colorId: Int) = mapWrapped { _.withColorId(colorId) }
	
	override def withCreated(created: Instant) = mapWrapped { _.withCreated(created) }
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = 
		mapWrapped { _.withDeprecatedAfter(deprecatedAfter) }
	
	override def withEnumValueId(enumValueId: Int) = mapWrapped { _.withEnumValueId(enumValueId) }
	
	override def withName(name: String) = mapWrapped { _.withName(name) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

