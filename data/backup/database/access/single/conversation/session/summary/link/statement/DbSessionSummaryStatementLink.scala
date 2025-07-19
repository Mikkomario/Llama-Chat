package vf.llama.database.access.single.conversation.session.summary.link.statement

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.SessionSummaryStatementLinkDbFactory
import vf.llama.database.storable.conversation.SessionSummaryStatementLinkDbModel
import vf.llama.model.stored.conversation.SessionSummaryStatementLink

/**
  * Used for accessing individual session summary statement links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbSessionSummaryStatementLink 
	extends SingleRowModelAccess[SessionSummaryStatementLink] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = SessionSummaryStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = SessionSummaryStatementLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted session summary statement link
	  * @return An access point to that session summary statement link
	  */
	def apply(id: Int) = DbSingleSessionSummaryStatementLink(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique session summary statement links.
	  * @return An access point to the session summary statement link that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueSessionSummaryStatementLinkAccess(condition)
}

