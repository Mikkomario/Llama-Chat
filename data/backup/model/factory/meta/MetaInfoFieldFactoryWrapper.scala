package vf.llama.model.factory.meta

import utopia.flow.util.Mutate
import vf.llama.model.enumeration.AutomatedMetaInfo

import java.time.Instant

/**
  * Common trait for classes that implement MetaInfoFieldFactory by wrapping a MetaInfoFieldFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoFieldFactoryWrapper[A <: MetaInfoFieldFactory[A], +Repr] extends MetaInfoFieldFactory[Repr]
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
	
	override def withAutomatedContent(automatedContent: AutomatedMetaInfo) = 
		mapWrapped { _.withAutomatedContent(automatedContent) }
	
	override def withCreated(created: Instant) = mapWrapped { _.withCreated(created) }
	
	override def withCustomInfoId(customInfoId: Int) = mapWrapped { _.withCustomInfoId(customInfoId) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

