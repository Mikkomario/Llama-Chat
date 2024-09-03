package vf.llama.database.access.many.meta.availability.thread

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.many.meta.availability.ManyMetaInfoAvailabilitiesAccessLike
import vf.llama.database.factory.meta.ThreadInfoAvailabilityDbFactory
import vf.llama.database.storable.meta.ThreadInfoAvailabilityDbModel
import vf.llama.model.stored.meta.ThreadInfoAvailability

object ManyThreadInfoAvailabilitiesAccess extends ViewFactory[ManyThreadInfoAvailabilitiesAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyThreadInfoAvailabilitiesAccess = 
		_ManyThreadInfoAvailabilitiesAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyThreadInfoAvailabilitiesAccess(override val accessCondition: Option[Condition]) 
		extends ManyThreadInfoAvailabilitiesAccess
}

/**
  * A common trait for access points which target multiple thread info availabilities at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyThreadInfoAvailabilitiesAccess 
	extends ManyMetaInfoAvailabilitiesAccessLike[ThreadInfoAvailability, ManyThreadInfoAvailabilitiesAccess] 
		with ManyRowModelAccess[ThreadInfoAvailability]
{
	// COMPUTED	--------------------
	
	/**
	  * thread ids of the accessible thread info availabilities
	  */
	def threadIds(implicit connection: Connection) = contextIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ThreadInfoAvailabilityDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = ThreadInfoAvailabilityDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyThreadInfoAvailabilitiesAccess = 
		ManyThreadInfoAvailabilitiesAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param threadId thread id to target
	  * @return Copy of this access point that only includes thread info availabilities 
		with the specified thread id
	  */
	def inThread(threadId: Int) = filter(model.threadId.column <=> threadId)
	
	/**
	  * @param threadIds Targeted thread ids
	  * 
		@return Copy of this access point that only includes thread info availabilities where thread id is within
	  *  the specified value set
	  */
	def inThreads(threadIds: IterableOnce[Int]) = filter(model.threadId.column.in(IntSet.from(threadIds)))
}

