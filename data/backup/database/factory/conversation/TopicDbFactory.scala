package vf.llama.database.factory.conversation

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.conversation.TopicDbModel
import vf.llama.model.partial.conversation.TopicData
import vf.llama.model.stored.conversation.Topic

/**
  * Used for reading topic data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object TopicDbFactory 
	extends FromValidatedRowModelFactory[Topic] with FromTimelineRowFactory[Topic] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = TopicDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		Topic(valid(this.model.id.name).getInt, TopicData(valid(this.model.created.name).getInstant, 
			valid(this.model.deprecatedAfter.name).instant))
}

