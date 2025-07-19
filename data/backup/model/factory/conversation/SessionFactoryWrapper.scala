package vf.llama.model.factory.conversation

import utopia.flow.util.Mutate

import java.time.Instant

/**
  * Common trait for classes that implement SessionFactory by wrapping a SessionFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait SessionFactoryWrapper[A <: SessionFactory[A], +Repr] extends SessionFactory[Repr]
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
	
	override def withConversationId(conversationId: Int) = mapWrapped { _.withConversationId(conversationId) }
	
	override def withEnded(ended: Instant) = mapWrapped { _.withEnded(ended) }
	
	override def withStarted(started: Instant) = mapWrapped { _.withStarted(started) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

