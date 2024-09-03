package vf.llama.database.factory.conversation

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.conversation.ConversationDbModel
import vf.llama.model.partial.conversation.ConversationData
import vf.llama.model.stored.conversation.Conversation

/**
  * Used for reading conversation data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ConversationDbFactory 
	extends FromValidatedRowModelFactory[Conversation] with FromTimelineRowFactory[Conversation] 
		with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = ConversationDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		Conversation(valid(this.model.id.name).getInt, 
			ConversationData(valid(this.model.threadId.name).getInt, 
			valid(this.model.created.name).getInstant, valid(this.model.closedAfter.name).instant))
}

