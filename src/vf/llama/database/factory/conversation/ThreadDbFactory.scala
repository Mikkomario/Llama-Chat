package vf.llama.database.factory.conversation

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.conversation.ThreadDbModel
import vf.llama.model.partial.conversation.ThreadData
import vf.llama.model.stored.conversation.Thread

/**
  * Used for reading thread data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ThreadDbFactory 
	extends FromValidatedRowModelFactory[Thread] with FromTimelineRowFactory[Thread] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = ThreadDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		Thread(valid(this.model.id.name).getInt, ThreadData(valid(this.model.topicId.name).getInt, 
			valid(this.model.name.name).getString, valid(this.model.created.name).getInstant, 
			valid(this.model.archivedAfter.name).instant))
}

