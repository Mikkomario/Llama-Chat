package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.conversation.topic.settings.DbSingleTopicSettings
import vf.llama.model.factory.conversation.TopicSettingsFactoryWrapper
import vf.llama.model.partial.conversation.TopicSettingsData

object TopicSettings extends StoredFromModelFactory[TopicSettingsData, TopicSettings]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = TopicSettingsData
	
	override protected def complete(model: AnyModel, data: TopicSettingsData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a topic settings that has already been stored in the database
  * @param id id of this topic settings in the database
  * @param data Wrapped topic settings data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicSettings(id: Int, data: TopicSettingsData) 
	extends StoredModelConvertible[TopicSettingsData] with FromIdFactory[Int, TopicSettings] 
		with TopicSettingsFactoryWrapper[TopicSettingsData, TopicSettings]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this topic settings in the database
	  */
	def access = DbSingleTopicSettings(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: TopicSettingsData) = copy(data = data)
}

