package vf.llama.database.access.single.conversation.session

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.SessionDbFactory
import vf.llama.model.stored.conversation.Session

object UniqueSessionAccess extends ViewFactory[UniqueSessionAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueSessionAccess = _UniqueSessionAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueSessionAccess(override val accessCondition: Option[Condition]) 
		extends UniqueSessionAccess
}

/**
  * A common trait for access points that return individual and distinct sessions.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueSessionAccess 
	extends UniqueSessionAccessLike[Session, UniqueSessionAccess] 
		with SingleChronoRowModelAccess[Session, UniqueSessionAccess] 
		with NullDeprecatableView[UniqueSessionAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = SessionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueSessionAccess = UniqueSessionAccess(condition)
}

