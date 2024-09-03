package vf.llama.database.access.single.conversation.session

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.SessionDbFactory
import vf.llama.database.storable.conversation.SessionDbModel
import vf.llama.model.stored.conversation.Session

/**
  * Used for accessing individual sessions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbSession extends SingleRowModelAccess[Session] with NonDeprecatedView[Session] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = SessionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = SessionDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted session
	  * @return An access point to that session
	  */
	def apply(id: Int) = DbSingleSession(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique sessions.
	  * @return An access point to the session that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueSessionAccess(mergeCondition(additionalCondition))
}

