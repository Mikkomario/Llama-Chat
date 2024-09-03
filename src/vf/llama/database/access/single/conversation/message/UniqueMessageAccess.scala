package vf.llama.database.access.single.conversation.message

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.MessageDbFactory
import vf.llama.model.stored.conversation.Message

object UniqueMessageAccess extends ViewFactory[UniqueMessageAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueMessageAccess = _UniqueMessageAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueMessageAccess(override val accessCondition: Option[Condition]) 
		extends UniqueMessageAccess
}

/**
  * A common trait for access points that return individual and distinct messages.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueMessageAccess 
	extends UniqueMessageAccessLike[Message, UniqueMessageAccess] 
		with SingleChronoRowModelAccess[Message, UniqueMessageAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MessageDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueMessageAccess = UniqueMessageAccess(condition)
}

