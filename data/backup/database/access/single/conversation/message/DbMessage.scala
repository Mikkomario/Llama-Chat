package vf.llama.database.access.single.conversation.message

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.MessageDbFactory
import vf.llama.database.storable.conversation.MessageDbModel
import vf.llama.model.stored.conversation.Message

/**
  * Used for accessing individual messages
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMessage extends SingleRowModelAccess[Message] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = MessageDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MessageDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted message
	  * @return An access point to that message
	  */
	def apply(id: Int) = DbSingleMessage(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique messages.
	  * @return An access point to the message that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueMessageAccess(condition)
}

