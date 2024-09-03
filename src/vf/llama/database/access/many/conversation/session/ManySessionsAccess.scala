package vf.llama.database.access.many.conversation.session

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.SessionDbFactory
import vf.llama.model.stored.conversation.Session

object ManySessionsAccess extends ViewFactory[ManySessionsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManySessionsAccess = _ManySessionsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManySessionsAccess(override val accessCondition: Option[Condition]) 
		extends ManySessionsAccess
}

/**
  * A common trait for access points which target multiple sessions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManySessionsAccess 
	extends ManySessionsAccessLike[Session, ManySessionsAccess] with ManyRowModelAccess[Session] 
		with ChronoRowFactoryView[Session, ManySessionsAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = SessionDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManySessionsAccess = ManySessionsAccess(condition)
}

