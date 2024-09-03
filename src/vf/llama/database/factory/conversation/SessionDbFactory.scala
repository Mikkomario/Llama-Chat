package vf.llama.database.factory.conversation

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.conversation.SessionDbModel
import vf.llama.model.partial.conversation.SessionData
import vf.llama.model.stored.conversation.Session

/**
  * Used for reading session data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object SessionDbFactory 
	extends FromValidatedRowModelFactory[Session] with FromTimelineRowFactory[Session] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = SessionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.started
	
	override protected def fromValidatedModel(valid: Model) = 
		Session(valid(this.model.id.name).getInt, SessionData(valid(this.model.conversationId.name).getInt, 
			valid(this.model.started.name).getInstant, valid(this.model.ended.name).instant))
}

