package vf.llama.database.storable.conversation

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.conversation.SessionFactory
import vf.llama.model.partial.conversation.SessionData
import vf.llama.model.stored.conversation.Session

import java.time.Instant

/**
  * Used for constructing SessionDbModel instances and for inserting sessions to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object SessionDbModel 
	extends StorableFactory[SessionDbModel, Session, SessionData] with FromIdFactory[Int, SessionDbModel] 
		with HasIdProperty with SessionFactory[SessionDbModel] with NullDeprecatable[SessionDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with conversation ids
	  */
	lazy val conversationId = property("conversationId")
	
	/**
	  * Database property used for interacting with start times
	  */
	lazy val started = property("started")
	
	/**
	  * Database property used for interacting with end times
	  */
	lazy val ended = property("ended")
	
	override val deprecationAttName = "ended"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.session
	
	override def apply(data: SessionData) = apply(None, Some(data.conversationId), Some(data.started), 
		data.ended)
	
	/**
	  * @param conversationId Id of the conversation active during this session
	  * @return A model containing only the specified conversation id
	  */
	override def withConversationId(conversationId: Int) = apply(conversationId = Some(conversationId))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withEnded(deprecationTime)
	
	/**
	  * @param ended Time when this session ended. None while active.
	  * @return A model containing only the specified ended
	  */
	override def withEnded(ended: Instant) = apply(ended = Some(ended))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param started Time when this session started
	  * @return A model containing only the specified started
	  */
	override def withStarted(started: Instant) = apply(started = Some(started))
	
	override protected def complete(id: Value, data: SessionData) = Session(id.getInt, data)
}

/**
  * Used for interacting with Sessions in the database
  * @param id session database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class SessionDbModel(id: Option[Int] = None, conversationId: Option[Int] = None, 
	started: Option[Instant] = None, ended: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, SessionDbModel] 
		with SessionFactory[SessionDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = SessionDbModel.table
	
	override def valueProperties = 
		Vector(SessionDbModel.id.name -> id, SessionDbModel.conversationId.name -> conversationId, 
			SessionDbModel.started.name -> started, SessionDbModel.ended.name -> ended)
	
	/**
	  * @param conversationId Id of the conversation active during this session
	  * @return A new copy of this model with the specified conversation id
	  */
	override def withConversationId(conversationId: Int) = copy(conversationId = Some(conversationId))
	
	/**
	  * @param ended Time when this session ended. None while active.
	  * @return A new copy of this model with the specified ended
	  */
	override def withEnded(ended: Instant) = copy(ended = Some(ended))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param started Time when this session started
	  * @return A new copy of this model with the specified started
	  */
	override def withStarted(started: Instant) = copy(started = Some(started))
}

