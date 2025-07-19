package vf.llama.database.access.many.conversation.summary

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import vf.llama.database.storable.conversation.ConversationSummaryDbModel

import java.time.Instant

/**
  * 
	A common trait for access points which target multiple conversation summaries or similar instances at a time
  * @tparam A Type of read (conversation summaries -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyConversationSummariesAccessLike[+A, +Repr] 
	extends ManyModelAccess[A] with Indexed with NullDeprecatableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * conversation ids of the accessible conversation summaries
	  */
	def conversationIds(implicit connection: Connection) = 
		pullColumn(model.conversationId.column).map { v => v.getInt }
	
	/**
	  * creation times of the accessible conversation summaries
	  */
	def creationTimes(implicit connection: Connection) = 
		pullColumn(model.created.column).map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible conversation summaries
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfter.column).flatMap { v => v.instant }
	
	/**
	  * Unique ids of the accessible conversation summaries
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
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
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfter.column, newDeprecatedAfter)
	
	/**
	  * @param conversationId conversation id to target
	  * @return Copy of this access point that only includes conversation summaries 
		with the specified conversation id
	  */
	def ofConversation(conversationId: Int) = filter(model.conversationId.column <=> conversationId)
	
	/**
	  * @param conversationIds Targeted conversation ids
	  * @return Copy of this access point that only includes conversation summaries where conversation id is
	  *  within the specified value set
	  */
	def ofConversations(conversationIds: IterableOnce[Int]) = 
		filter(model.conversationId.column.in(IntSet.from(conversationIds)))
}

