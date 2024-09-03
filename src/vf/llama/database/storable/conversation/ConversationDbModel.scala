package vf.llama.database.storable.conversation

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.conversation.ConversationFactory
import vf.llama.model.partial.conversation.ConversationData
import vf.llama.model.stored.conversation.Conversation

import java.time.Instant

/**
  * Used for constructing ConversationDbModel instances and for inserting conversations to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ConversationDbModel 
	extends StorableFactory[ConversationDbModel, Conversation, ConversationData] 
		with FromIdFactory[Int, ConversationDbModel] with HasIdProperty 
		with ConversationFactory[ConversationDbModel] with NullDeprecatable[ConversationDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with thread ids
	  */
	lazy val threadId = property("threadId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with closing times
	  */
	lazy val closedAfter = property("closedAfter")
	
	override val deprecationAttName = "closedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.conversation
	
	override def apply(data: ConversationData) = 
		apply(None, Some(data.threadId), Some(data.created), data.closedAfter)
	
	/**
	  * @param closedAfter Time when this conversation was closed the last time
	  * @return A model containing only the specified closed after
	  */
	override def withClosedAfter(closedAfter: Instant) = apply(closedAfter = Some(closedAfter))
	
	/**
	  * @param created Time when this conversation was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withClosedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param threadId Id of the thread this conversation is part of
	  * @return A model containing only the specified thread id
	  */
	override def withThreadId(threadId: Int) = apply(threadId = Some(threadId))
	
	override protected def complete(id: Value, data: ConversationData) = Conversation(id.getInt, data)
}

/**
  * Used for interacting with Conversations in the database
  * @param id conversation database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ConversationDbModel(id: Option[Int] = None, threadId: Option[Int] = None, 
	created: Option[Instant] = None, closedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, ConversationDbModel] 
		with ConversationFactory[ConversationDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = ConversationDbModel.table
	
	override def valueProperties = 
		Vector(ConversationDbModel.id.name -> id, ConversationDbModel.threadId.name -> threadId, 
			ConversationDbModel.created.name -> created, ConversationDbModel.closedAfter.name -> closedAfter)
	
	/**
	  * @param closedAfter Time when this conversation was closed the last time
	  * @return A new copy of this model with the specified closed after
	  */
	override def withClosedAfter(closedAfter: Instant) = copy(closedAfter = Some(closedAfter))
	
	/**
	  * @param created Time when this conversation was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param threadId Id of the thread this conversation is part of
	  * @return A new copy of this model with the specified thread id
	  */
	override def withThreadId(threadId: Int) = copy(threadId = Some(threadId))
}

