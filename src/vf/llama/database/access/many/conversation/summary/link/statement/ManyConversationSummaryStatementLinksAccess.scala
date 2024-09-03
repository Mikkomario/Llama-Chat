package vf.llama.database.access.many.conversation.summary.link.statement

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.access.many.statement.ManyStatementLinksAccessLike
import vf.llama.database.factory.conversation.ConversationSummaryStatementLinkDbFactory
import vf.llama.database.storable.conversation.ConversationSummaryStatementLinkDbModel
import vf.llama.model.enumeration.SummaryStatementRole
import vf.llama.model.stored.conversation.ConversationSummaryStatementLink

object ManyConversationSummaryStatementLinksAccess 
	extends ViewFactory[ManyConversationSummaryStatementLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyConversationSummaryStatementLinksAccess = 
		_ManyConversationSummaryStatementLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyConversationSummaryStatementLinksAccess(override
		 val accessCondition: Option[Condition]) 
		extends ManyConversationSummaryStatementLinksAccess
}

/**
  * A common trait for access points which target multiple conversation summary statement links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyConversationSummaryStatementLinksAccess 
	extends ManyStatementLinksAccessLike[ConversationSummaryStatementLink, ManyConversationSummaryStatementLinksAccess] 
		with ManyRowModelAccess[ConversationSummaryStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * summary ids of the accessible conversation summary statement links
	  */
	def summaryIds(implicit connection: Connection) = parentIds
	
	/**
	  * roles of the accessible conversation summary statement links
	  */
	def roles(implicit connection: Connection) = 
		pullColumn(model.role.column).map { v => v.getInt }.flatMap(SummaryStatementRole.findForId)
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ConversationSummaryStatementLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = ConversationSummaryStatementLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyConversationSummaryStatementLinksAccess = 
		ManyConversationSummaryStatementLinksAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param summaryIds Targeted summary ids
	  * 
		@return Copy of this access point that only includes conversation summary statement links where summary id is
	  *  within the specified value set
	  */
	def inSummaries(summaryIds: IterableOnce[Int]) = filter(model
		.summaryId.column.in(IntSet.from(summaryIds)))
	
	/**
	  * @param summaryId summary id to target
	  * @return Copy of this access point that only includes conversation summary statement links 
	  * with the specified summary id
	  */
	def inSummary(summaryId: Int) = filter(model.summaryId.column <=> summaryId)
	
	/**
	  * @param role role to target
	  * @return Copy of this access point that only includes conversation summary statement links 
	  * with the specified role
	  */
	def withRole(role: SummaryStatementRole) = filter(model.role.column <=> role.id)
	
	/**
	  * @param roles Targeted roles
	  * 
		@return Copy of this access point that only includes conversation summary statement links where role is within
	  *  the specified value set
	  */
	def withRoles(roles: Iterable[SummaryStatementRole]) = 
		filter(model.role.column.in(roles.map { role => role.id }))
}

