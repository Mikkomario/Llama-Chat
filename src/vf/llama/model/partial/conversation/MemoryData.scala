package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.{Pair, Single}
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import vf.llama.model.factory.conversation.MemoryFactory

import java.time.Instant

object MemoryData extends FromModelFactoryWithSchema[MemoryData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Pair(PropertyDeclaration("messageId", IntType, Single("message_id")), 
			PropertyDeclaration("archivedAfter", InstantType, Single("archived_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		MemoryData(valid("messageId").getInt, valid("archivedAfter").instant)
}

/**
  * Represents a memory recorded during a conversation
  * @param messageId Id of the message from which this memory was triggered
  * @param archivedAfter Time when this memory was archived
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MemoryData(messageId: Int, archivedAfter: Option[Instant] = None) 
	extends MemoryFactory[MemoryData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this memory has already been deprecated
	  */
	def isDeprecated = archivedAfter.isDefined
	
	/**
	  * Whether this memory is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Pair("messageId" -> messageId, "archivedAfter" -> archivedAfter))
	
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	override def withMessageId(messageId: Int) = copy(messageId = messageId)
}

