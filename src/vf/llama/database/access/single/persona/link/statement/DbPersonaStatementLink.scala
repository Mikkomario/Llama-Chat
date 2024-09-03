package vf.llama.database.access.single.persona.link.statement

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaStatementLinkDbFactory
import vf.llama.database.storable.persona.PersonaStatementLinkDbModel
import vf.llama.model.stored.persona.PersonaStatementLink

/**
  * Used for accessing individual persona statement links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaStatementLink 
	extends SingleRowModelAccess[PersonaStatementLink] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaStatementLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona statement link
	  * @return An access point to that persona statement link
	  */
	def apply(id: Int) = DbSinglePersonaStatementLink(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona statement links.
	  * @return An access point to the persona statement link that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniquePersonaStatementLinkAccess(condition)
}

