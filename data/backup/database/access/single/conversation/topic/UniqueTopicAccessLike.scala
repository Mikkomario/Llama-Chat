package vf.llama.database.access.single.conversation.topic

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.conversation.TopicDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual topics or similar items at a time
  * @tparam A Type of read (topics -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueTopicAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Time when this topic was added to the database. 
	  * None if no topic (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this topic was archived. 
	  * None if no topic (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfter.column).instant
	
	/**
	  * Unique id of the accessible topic. None if no topic was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = TopicDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted topics
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any topic was affected
	  */
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
}

