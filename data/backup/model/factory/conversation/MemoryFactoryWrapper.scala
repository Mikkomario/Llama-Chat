package vf.llama.model.factory.conversation

import utopia.flow.util.Mutate

import java.time.Instant

/**
  * Common trait for classes that implement MemoryFactory by wrapping a MemoryFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MemoryFactoryWrapper[A <: MemoryFactory[A], +Repr] extends MemoryFactory[Repr]
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
	
	override def withArchivedAfter(archivedAfter: Instant) = mapWrapped { _.withArchivedAfter(archivedAfter) }
	
	override def withMessageId(messageId: Int) = mapWrapped { _.withMessageId(messageId) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

