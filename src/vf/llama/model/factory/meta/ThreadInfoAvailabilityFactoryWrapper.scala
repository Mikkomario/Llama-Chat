package vf.llama.model.factory.meta

/**
  * Common trait for classes that implement ThreadInfoAvailabilityFactory by wrapping
  *  a ThreadInfoAvailabilityFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ThreadInfoAvailabilityFactoryWrapper[A <: ThreadInfoAvailabilityFactory[A], +Repr] 
	extends ThreadInfoAvailabilityFactory[Repr] with MetaInfoAvailabilityFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withThreadId(threadId: Int) = withContextId(threadId)
}

