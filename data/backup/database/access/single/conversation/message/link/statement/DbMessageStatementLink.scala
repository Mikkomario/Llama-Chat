package vf.llama.database.access.single.conversation.message.link.statement

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.MessageStatementLinkDbFactory
import vf.llama.database.storable.conversation.MessageStatementLinkDbModel
import vf.llama.model.stored.conversation.MessageStatementLink

/**
  * Used for accessing individual message statement links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMessageStatementLink 
	extends SingleRowModelAccess[MessageStatementLink] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = MessageStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MessageStatementLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted message statement link
	  * @return An access point to that message statement link
	  */
	def apply(id: Int) = DbSingleMessageStatementLink(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique message statement links.
	  * @return An access point to the message statement link that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueMessageStatementLinkAccess(condition)
}

