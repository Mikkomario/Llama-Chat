package vf.llama.database.access.single.meta.availability

import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.props.meta.MetaInfoAvailabilityDbProps

/**
  * 
	A common trait for access points which target individual meta info availabilities or similar items at a time
  * @tparam A Type of read (meta info availabilities -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueMetaInfoAvailabilityAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// ABSTRACT	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model: MetaInfoAvailabilityDbProps
	
	
	// COMPUTED	--------------------
	
	/**
	  * Id of the meta info -field made available. 
	  * None if no meta info availability (or value) was found.
	  */
	def fieldId(implicit connection: Connection) = pullColumn(model.fieldId.column).int
	
	/**
	  * Id of the context where the linked information is made available. 
	  * None if no meta info availability (or value) was found.
	  */
	def contextId(implicit connection: Connection) = pullColumn(model.contextId.column).int
	
	/**
	  * Time when this information was made available. 
	  * None if no meta info availability (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Unique id of the accessible meta info availability. None if no meta info availability was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
}

