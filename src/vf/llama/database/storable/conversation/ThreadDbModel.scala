package vf.llama.database.storable.conversation

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.conversation.ThreadFactory
import vf.llama.model.partial.conversation.ThreadData
import vf.llama.model.stored.conversation.Thread

import java.time.Instant

/**
  * Used for constructing ThreadDbModel instances and for inserting threads to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ThreadDbModel 
	extends StorableFactory[ThreadDbModel, Thread, ThreadData] with FromIdFactory[Int, ThreadDbModel] 
		with HasIdProperty with ThreadFactory[ThreadDbModel] with NullDeprecatable[ThreadDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with topic ids
	  */
	lazy val topicId = property("topicId")
	
	/**
	  * Database property used for interacting with names
	  */
	lazy val name = property("name")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with archive times
	  */
	lazy val archivedAfter = property("archivedAfter")
	
	override val deprecationAttName = "archivedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.thread
	
	override def apply(data: ThreadData) = 
		apply(None, Some(data.topicId), data.name, Some(data.created), data.archivedAfter)
	
	/**
	  * @param archivedAfter Time when this thread was archived
	  * @return A model containing only the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = apply(archivedAfter = Some(archivedAfter))
	
	/**
	  * @param created Time when this thread was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withArchivedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param name The current name of this thread
	  * @return A model containing only the specified name
	  */
	override def withName(name: String) = apply(name = name)
	
	/**
	  * @param topicId Id of the general topic of this thread
	  * @return A model containing only the specified topic id
	  */
	override def withTopicId(topicId: Int) = apply(topicId = Some(topicId))
	
	override protected def complete(id: Value, data: ThreadData) = Thread(id.getInt, data)
}

/**
  * Used for interacting with Threads in the database
  * @param id thread database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ThreadDbModel(id: Option[Int] = None, topicId: Option[Int] = None, name: String = "", 
	created: Option[Instant] = None, archivedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, ThreadDbModel] 
		with ThreadFactory[ThreadDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = ThreadDbModel.table
	
	override def valueProperties = 
		Vector(ThreadDbModel.id.name -> id, ThreadDbModel.topicId.name -> topicId, 
			ThreadDbModel.name.name -> name, ThreadDbModel.created.name -> created, 
			ThreadDbModel.archivedAfter.name -> archivedAfter)
	
	/**
	  * @param archivedAfter Time when this thread was archived
	  * @return A new copy of this model with the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	/**
	  * @param created Time when this thread was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param name The current name of this thread
	  * @return A new copy of this model with the specified name
	  */
	override def withName(name: String) = copy(name = name)
	
	/**
	  * @param topicId Id of the general topic of this thread
	  * @return A new copy of this model with the specified topic id
	  */
	override def withTopicId(topicId: Int) = copy(topicId = Some(topicId))
}

