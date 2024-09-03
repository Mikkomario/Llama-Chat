package vf.llama.database.access.single.conversation.memory.link.statement

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.conversation.MemoryStatementLinkDbFactory
import vf.llama.database.storable.conversation.MemoryStatementLinkDbModel
import vf.llama.model.stored.conversation.MemoryStatementLink

/**
  * Used for accessing individual memory statement links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMemoryStatementLink 
	extends SingleRowModelAccess[MemoryStatementLink] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = MemoryStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MemoryStatementLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted memory statement link
	  * @return An access point to that memory statement link
	  */
	def apply(id: Int) = DbSingleMemoryStatementLink(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique memory statement links.
	  * @return An access point to the memory statement link that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueMemoryStatementLinkAccess(condition)
}

