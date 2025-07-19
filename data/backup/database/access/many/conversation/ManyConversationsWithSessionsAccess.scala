package vf.llama.database.access.many.conversation

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.ConversationWithSessionsDbFactory
import vf.llama.database.storable.conversation.SessionDbModel
import vf.llama.model.combined.conversation.ConversationWithSessions

import java.time.Instant

object ManyConversationsWithSessionsAccess extends ViewFactory[ManyConversationsWithSessionsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyConversationsWithSessionsAccess = 
		_ManyConversationsWithSessionsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyConversationsWithSessionsAccess(override val accessCondition: Option[Condition]) 
		extends ManyConversationsWithSessionsAccess
}

/**
  * A common trait for access points that return multiple conversations with sessions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024
  */
trait ManyConversationsWithSessionsAccess 
	extends ManyConversationsAccessLike[ConversationWithSessions, ManyConversationsWithSessionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * conversation ids of the accessible sessions
	  */
	def sessionConversationIds(implicit connection: Connection) = 
		pullColumn(sessionModel.conversationId.column).map { v => v.getInt }
	
	/**
	  * start times of the accessible sessions
	  */
	def sessionStartTimes(implicit connection: Connection) = 
		pullColumn(sessionModel.started.column).map { v => v.getInstant }
	
	/**
	  * end times of the accessible sessions
	  */
	def sessionEndTimes(implicit connection: Connection) = 
		pullColumn(sessionModel.ended.column).flatMap { v => v.instant }
	
	/**
	  * Model (factory) used for interacting the sessions associated with this conversation with sessions
	  */
	protected def sessionModel = SessionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationWithSessionsDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyConversationsWithSessionsAccess = 
		ManyConversationsWithSessionsAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the end times of the targeted sessions
	  * @param newEnded A new ended to assign
	  * @return Whether any session was affected
	  */
	def sessionEndTimes_=(newEnded: Instant)(implicit connection: Connection) = 
		putColumn(sessionModel.ended.column, newEnded)
}

