package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.conversation.topic.DbSingleTopic
import vf.llama.model.factory.conversation.TopicFactoryWrapper
import vf.llama.model.partial.conversation.TopicData

object Topic extends StoredFromModelFactory[TopicData, Topic]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = TopicData
	
	override protected def complete(model: AnyModel, data: TopicData) = model("id").tryInt.map { apply(_, 
		data) }
}

/**
  * Represents a topic that has already been stored in the database
  * @param id id of this topic in the database
  * @param data Wrapped topic data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Topic(id: Int, data: TopicData) 
	extends StoredModelConvertible[TopicData] with FromIdFactory[Int, Topic] 
		with TopicFactoryWrapper[TopicData, Topic]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this topic in the database
	  */
	def access = DbSingleTopic(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: TopicData) = copy(data = data)
}

