package vf.llama.database.access.single.conversation.summary

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.llama.database.storable.conversation.ConversationSummaryDbModel

import java.time.Instant

/**
  * A common trait for access points which target individual conversation summaries or similar items at a time
  * @tparam A Type of read (conversation summaries -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueConversationSummaryAccessLike[+A, +Repr] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with FilterableView[Repr] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the conversation this is a summary of. 
	  * None if no conversation summary (or value) was found.
	  */
	def conversationId(implicit connection: Connection) = pullColumn(model.conversationId.column).int
	
	/**
	  * Time when this conversation summary was added to the database. 
	  * None if no conversation summary (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.created.column).instant
	
	/**
	  * Time when this summary was replaced with a new version. 
	  * None if no conversation summary (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfter.column).instant
	
	/**
	  * Unique id of the accessible conversation summary. None if no conversation summary was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = ConversationSummaryDbModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the deprecation times of the targeted conversation summaries
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any conversation summary was affected
	  */
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
}

