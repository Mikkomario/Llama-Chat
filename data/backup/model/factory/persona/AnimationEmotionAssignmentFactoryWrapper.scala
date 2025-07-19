package vf.llama.model.factory.persona

import utopia.flow.util.Mutate

import java.time.Instant

/**
  * Common trait for classes that implement AnimationEmotionAssignmentFactory by wrapping
  *  a AnimationEmotionAssignmentFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait AnimationEmotionAssignmentFactoryWrapper[A <: AnimationEmotionAssignmentFactory[A], +Repr] 
	extends AnimationEmotionAssignmentFactory[Repr]
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
	
	override def withCreated(created: Instant) = mapWrapped { _.withCreated(created) }
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = 
		mapWrapped { _.withDeprecatedAfter(deprecatedAfter) }
	
	override def withEmotion(emotion: String) = mapWrapped { _.withEmotion(emotion) }
	
	override def withOriginLlmId(originLlmId: Int) = mapWrapped { _.withOriginLlmId(originLlmId) }
	
	
	// OTHER	--------------------
	
	/**
	  * Modifies this item by mutating the wrapped factory instance
	  * @param f A function for mutating the wrapped factory instance
	  * @return Copy of this item with a mutated wrapped factory
	  */
	protected def mapWrapped(f: Mutate[A]) = wrap(f(wrappedFactory))
}

