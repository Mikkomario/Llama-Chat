package vf.llama.database.storable.conversation

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.conversation.TopicFactory
import vf.llama.model.partial.conversation.TopicData
import vf.llama.model.stored.conversation.Topic

import java.time.Instant

/**
  * Used for constructing TopicDbModel instances and for inserting topics to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object TopicDbModel 
	extends StorableFactory[TopicDbModel, Topic, TopicData] with FromIdFactory[Int, TopicDbModel] 
		with HasIdProperty with TopicFactory[TopicDbModel] with DeprecatableAfter[TopicDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with deprecation times
	  */
	lazy val deprecatedAfter = property("deprecatedAfter")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.topic
	
	override def apply(data: TopicData) = apply(None, Some(data.created), data.deprecatedAfter)
	
	/**
	  * @param created Time when this topic was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this topic was archived
	  * @return A model containing only the specified deprecated after
	  */
	override
		 def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	override protected def complete(id: Value, data: TopicData) = Topic(id.getInt, data)
}

/**
  * Used for interacting with Topics in the database
  * @param id topic database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class TopicDbModel(id: Option[Int] = None, created: Option[Instant] = None, 
	deprecatedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, TopicDbModel] 
		with TopicFactory[TopicDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = TopicDbModel.table
	
	override def valueProperties = 
		Vector(TopicDbModel.id.name -> id, TopicDbModel.created.name -> created, 
			TopicDbModel.deprecatedAfter.name -> deprecatedAfter)
	
	/**
	  * @param created Time when this topic was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this topic was archived
	  * @return A new copy of this model with the specified deprecated after
	  */
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = copy(id = Some(id))
}

