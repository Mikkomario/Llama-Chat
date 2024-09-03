package vf.llama.model.combined.conversation

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.conversation.SessionFactoryWrapper
import vf.llama.model.partial.conversation.SessionData
import vf.llama.model.stored.conversation.Session

/**
  * Common trait for combinations that add additional data to sessions
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedSession[+Repr] 
	extends Extender[SessionData] with HasId[Int] with SessionFactoryWrapper[Session, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped session
	  */
	def session: Session
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this session in the database
	  */
	override def id = session.id
	
	override def wrapped = session.data
	
	override protected def wrappedFactory = session
}

