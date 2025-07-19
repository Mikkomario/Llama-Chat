package vf.llama.database.factory.conversation

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.conversation.TopicSettingsDbModel
import vf.llama.model.partial.conversation.TopicSettingsData
import vf.llama.model.stored.conversation.TopicSettings

/**
  * Used for reading topic settings data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object TopicSettingsDbFactory 
	extends FromValidatedRowModelFactory[TopicSettings] with FromTimelineRowFactory[TopicSettings] 
		with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = TopicSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		TopicSettings(valid(this.model.id.name).getInt, 
			TopicSettingsData(valid(this.model.topicId.name).getInt, valid(this.model.personaId.name).getInt, 
			valid(this.model.name.name).getString, valid(this.model.created.name).getInstant, 
			valid(this.model.deprecatedAfter.name).instant))
}

