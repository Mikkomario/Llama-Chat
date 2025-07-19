package vf.llama.database.access.single.conversation.message

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.StatementLinkedMessageDbFactory
import vf.llama.database.storable.conversation.{MessageDbModel, MessageStatementLinkDbModel}
import vf.llama.model.combined.conversation.StatementLinkedMessage

/**
  * Used for accessing individual statement linked messages
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbStatementLinkedMessage 
	extends SingleModelAccess[StatementLinkedMessage] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = MessageStatementLinkDbModel
	
	/**
	  * A database model (factory) used for interacting with linked messages
	  */
	private def model = MessageDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedMessageDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted statement linked message
	  * @return An access point to that statement linked message
	  */
	def apply(id: Int) = DbSingleStatementLinkedMessage(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique statement linked messages.
	  * @return An access point to the statement linked message that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueStatementLinkedMessageAccess(condition)
}

