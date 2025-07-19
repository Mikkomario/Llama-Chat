package vf.llama.database.storable.conversation

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.conversation.ConversationSummaryFactory
import vf.llama.model.partial.conversation.ConversationSummaryData
import vf.llama.model.stored.conversation.ConversationSummary

import java.time.Instant

/**
  * Used for constructing ConversationSummaryDbModel instances and for inserting conversation summaries to
  *  the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ConversationSummaryDbModel 
	extends StorableFactory[ConversationSummaryDbModel, ConversationSummary, ConversationSummaryData] 
		with FromIdFactory[Int, ConversationSummaryDbModel] with HasIdProperty 
		with ConversationSummaryFactory[ConversationSummaryDbModel] 
		with DeprecatableAfter[ConversationSummaryDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with conversation ids
	  */
	lazy val conversationId = property("conversationId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with deprecation times
	  */
	lazy val deprecatedAfter = property("deprecatedAfter")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.conversationSummary
	
	override def apply(data: ConversationSummaryData) = 
		apply(None, Some(data.conversationId), Some(data.created), data.deprecatedAfter)
	
	/**
	  * @param conversationId Id of the conversation this is a summary of
	  * @return A model containing only the specified conversation id
	  */
	override def withConversationId(conversationId: Int) = apply(conversationId = Some(conversationId))
	
	/**
	  * @param created Time when this conversation summary was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this summary was replaced with a new version
	  * @return A model containing only the specified deprecated after
	  */
	override
		 def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	override protected def complete(id: Value, data: ConversationSummaryData) = 
		ConversationSummary(id.getInt, data)
}

/**
  * Used for interacting with ConversationSummaries in the database
  * @param id conversation summary database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ConversationSummaryDbModel(id: Option[Int] = None, conversationId: Option[Int] = None, 
	created: Option[Instant] = None, deprecatedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, ConversationSummaryDbModel] 
		with ConversationSummaryFactory[ConversationSummaryDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = ConversationSummaryDbModel.table
	
	override def valueProperties = 
		Vector(ConversationSummaryDbModel.id.name -> id, 
			ConversationSummaryDbModel.conversationId.name -> conversationId, 
			ConversationSummaryDbModel.created.name -> created, 
			ConversationSummaryDbModel.deprecatedAfter.name -> deprecatedAfter)
	
	/**
	  * @param conversationId Id of the conversation this is a summary of
	  * @return A new copy of this model with the specified conversation id
	  */
	override def withConversationId(conversationId: Int) = copy(conversationId = Some(conversationId))
	
	/**
	  * @param created Time when this conversation summary was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this summary was replaced with a new version
	  * @return A new copy of this model with the specified deprecated after
	  */
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = copy(id = Some(id))
}

