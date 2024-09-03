package vf.llama.database.access.many.meta.availability

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.props.meta.MetaInfoAvailabilityDbProps

/**
  * 
	A common trait for access points which target multiple meta info availabilities or similar instances at a time
  * @tparam A Type of read (meta info availabilities -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyMetaInfoAvailabilitiesAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with FilterableView[Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model: MetaInfoAvailabilityDbProps
	
	
	// COMPUTED	--------------------
	
	/**
	  * field ids of the accessible meta info availabilities
	  */
	def fieldIds(implicit connection: Connection) = pullColumn(model.fieldId.column).map { v => v.getInt }
	
	/**
	  * context ids of the accessible meta info availabilities
	  */
	def contextIds(implicit connection: Connection) = pullColumn(model.contextId.column).map { v => v.getInt }
	
	/**
	  * creation times of the accessible meta info availabilities
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * Unique ids of the accessible meta info availabilities
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	
	// OTHER	--------------------
	
	/**
	  * @param contextId context id to target
	  * @return Copy of this access point that only includes meta info availabilities 
		with the specified context id
	  */
	def inContext(contextId: Int) = filter(model.contextId.column <=> contextId)
	
	/**
	  * @param contextIds Targeted context ids
	  * 
		@return Copy of this access point that only includes meta info availabilities where context id is within
	  *  the specified value set
	  */
	def inContexts(contextIds: IterableOnce[Int]) = filter(model.contextId.column.in(IntSet.from(contextIds)))
	
	/**
	  * @param fieldId field id to target
	  * @return Copy of this access point that only includes meta info availabilities 
		with the specified field id
	  */
	def ofField(fieldId: Int) = filter(model.fieldId.column <=> fieldId)
	
	/**
	  * @param fieldIds Targeted field ids
	  * @return Copy of this access point that only includes meta info availabilities where field id is within
	  *  the specified value set
	  */
	def ofFields(fieldIds: IterableOnce[Int]) = filter(model.fieldId.column.in(IntSet.from(fieldIds)))
}

