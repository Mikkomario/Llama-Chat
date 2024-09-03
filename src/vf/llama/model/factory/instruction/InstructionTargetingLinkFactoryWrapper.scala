package vf.llama.model.factory.instruction

import utopia.flow.util.Mutate

import java.time.Instant

/**
  * Common trait for classes that implement InstructionTargetingLinkFactory by wrapping
  *  a InstructionTargetingLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkFactoryWrapper[A <: InstructionTargetingLinkFactory[A], +Repr] 
	extends InstructionTargetingLinkFactory[Repr]
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
	
	override def withInstructionId(instructionId: Int) = mapWrapped { _.withInstructionId(instructionId) }
	
	override def withRemovedAfter(removedAfter: Instant) = mapWrapped { _.withRemovedAfter(removedAfter) }
	
	override def withTargetId(targetId: Int) = mapWrapped { _.withTargetId(targetId) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

