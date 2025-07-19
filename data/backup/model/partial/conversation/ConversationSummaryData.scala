package vf.llama.model.partial.conversation

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.factory.conversation.ConversationSummaryFactory

import java.time.Instant

object ConversationSummaryData extends FromModelFactoryWithSchema[ConversationSummaryData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("conversationId", IntType, Single("conversation_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("deprecatedAfter", InstantType, Single("deprecated_after"), 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		ConversationSummaryData(valid("conversationId").getInt, valid("created").getInstant, 
			valid("deprecatedAfter").instant)
}

/**
  * Summarizes a conversation
  * @param conversationId Id of the conversation this is a summary of
  * @param created Time when this conversation summary was added to the database
  * @param deprecatedAfter Time when this summary was replaced with a new version
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ConversationSummaryData(conversationId: Int, created: Instant = Now, 
	deprecatedAfter: Option[Instant] = None) 
	extends ConversationSummaryFactory[ConversationSummaryData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this conversation summary has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this conversation summary is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("conversationId" -> conversationId, "created" -> created, 
			"deprecatedAfter" -> deprecatedAfter))
	
	override def withConversationId(conversationId: Int) = copy(conversationId = conversationId)
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
}

