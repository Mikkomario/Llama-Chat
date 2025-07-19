package vf.llama.database.access.many.conversation.topic

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.conversation.TopicDbModel

import java.time.Instant

/**
  * A common trait for access points which target multiple topics or similar instances at a time
  * @tparam A Type of read (topics -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyTopicsAccessLike[+A, +Repr] extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * creation times of the accessible topics
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible topics
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible topics
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
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
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
}

