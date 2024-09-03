package vf.llama.database.factory.conversation

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.conversation.ConversationSummaryDbModel
import vf.llama.model.partial.conversation.ConversationSummaryData
import vf.llama.model.stored.conversation.ConversationSummary

/**
  * Used for reading conversation summary data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ConversationSummaryDbFactory 
	extends FromValidatedRowModelFactory[ConversationSummary] 
		with FromTimelineRowFactory[ConversationSummary] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = ConversationSummaryDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		ConversationSummary(valid(this.model.id.name).getInt, 
			ConversationSummaryData(valid(this.model.conversationId.name).getInt, 
			valid(this.model.created.name).getInstant, valid(this.model.deprecatedAfter.name).instant))
}

