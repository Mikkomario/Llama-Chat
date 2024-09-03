package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.{FromIdFactory, StoredFromModelFactory, StoredModelConvertible}
import vf.llama.database.access.single.conversation.session.DbSingleSession
import vf.llama.model.factory.conversation.SessionFactoryWrapper
import vf.llama.model.partial.conversation.SessionData

object Session extends StoredFromModelFactory[SessionData, Session]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = SessionData
	
	override protected def complete(model: AnyModel, data: SessionData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a session that has already been stored in the database
  * @param id id of this session in the database
  * @param data Wrapped session data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class Session(id: Int, data: SessionData) 
	extends StoredModelConvertible[SessionData] with FromIdFactory[Int, Session] 
		with SessionFactoryWrapper[SessionData, Session]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this session in the database
	  */
	def access = DbSingleSession(id)
	
	
	// IMPLEMENTED	--------------------
	
	override protected def wrappedFactory = data
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: SessionData) = copy(data = data)
}

