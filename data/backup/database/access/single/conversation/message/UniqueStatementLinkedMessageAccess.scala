package vf.llama.database.access.single.conversation.message

import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.StatementLinkedMessageDbFactory
import vf.llama.database.storable.conversation.MessageStatementLinkDbModel
import vf.llama.model.combined.conversation.StatementLinkedMessage

object UniqueStatementLinkedMessageAccess extends ViewFactory[UniqueStatementLinkedMessageAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueStatementLinkedMessageAccess = 
		_UniqueStatementLinkedMessageAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueStatementLinkedMessageAccess(override val accessCondition: Option[Condition]) 
		extends UniqueStatementLinkedMessageAccess
}

/**
  * A common trait for access points that return distinct statement linked messages
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueStatementLinkedMessageAccess 
	extends UniqueMessageAccessLike[StatementLinkedMessage, UniqueStatementLinkedMessageAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked link
	  */
	protected def linkModel = MessageStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StatementLinkedMessageDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueStatementLinkedMessageAccess = 
		UniqueStatementLinkedMessageAccess(condition)
}

