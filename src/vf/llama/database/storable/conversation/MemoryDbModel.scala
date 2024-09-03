package vf.llama.database.storable.conversation

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.conversation.MemoryFactory
import vf.llama.model.partial.conversation.MemoryData
import vf.llama.model.stored.conversation.Memory

import java.time.Instant

/**
  * Used for constructing MemoryDbModel instances and for inserting memories to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object MemoryDbModel 
	extends StorableFactory[MemoryDbModel, Memory, MemoryData] with FromIdFactory[Int, MemoryDbModel] 
		with HasIdProperty with MemoryFactory[MemoryDbModel] with NullDeprecatable[MemoryDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with message ids
	  */
	lazy val messageId = property("messageId")
	
	/**
	  * Database property used for interacting with archive times
	  */
	lazy val archivedAfter = property("archivedAfter")
	
	override val deprecationAttName = "archivedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.memory
	
	override def apply(data: MemoryData) = apply(None, Some(data.messageId), data.archivedAfter)
	
	/**
	  * @param archivedAfter Time when this memory was archived
	  * @return A model containing only the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = apply(archivedAfter = Some(archivedAfter))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withArchivedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param messageId Id of the message from which this memory was triggered
	  * @return A model containing only the specified message id
	  */
	override def withMessageId(messageId: Int) = apply(messageId = Some(messageId))
	
	override protected def complete(id: Value, data: MemoryData) = Memory(id.getInt, data)
}

/**
  * Used for interacting with Memories in the database
  * @param id memory database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MemoryDbModel(id: Option[Int] = None, messageId: Option[Int] = None, 
	archivedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, MemoryDbModel] 
		with MemoryFactory[MemoryDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = MemoryDbModel.table
	
	override def valueProperties = 
		Vector(MemoryDbModel.id.name -> id, MemoryDbModel.messageId.name -> messageId, 
			MemoryDbModel.archivedAfter.name -> archivedAfter)
	
	/**
	  * @param archivedAfter Time when this memory was archived
	  * @return A new copy of this model with the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param messageId Id of the message from which this memory was triggered
	  * @return A new copy of this model with the specified message id
	  */
	override def withMessageId(messageId: Int) = copy(messageId = Some(messageId))
}

