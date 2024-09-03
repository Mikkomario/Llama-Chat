package vf.llama.database.factory.conversation

import utopia.echo.model.enumeration.ChatRole
import utopia.flow.generic.model.template.{ModelLike, Property}
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromRowModelFactory
import vf.llama.database.storable.conversation.MessageDbModel
import vf.llama.model.partial.conversation.MessageData
import vf.llama.model.stored.conversation.Message

/**
  * Used for reading message data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object MessageDbFactory extends FromRowModelFactory[Message] with FromTimelineRowFactory[Message]
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = MessageDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override def apply(model: ModelLike[Property]) = {
		table.validate(model).flatMap { valid => 
			ChatRole.fromValue(valid(this.model.sender.name)).map { sender =>
				Message(valid(this.model.id.name).getInt, 
					MessageData(valid(this.model.sessionId.name).getInt, sender, 
					valid(this.model.created.name).getInstant))
			}
		}
	}
}

