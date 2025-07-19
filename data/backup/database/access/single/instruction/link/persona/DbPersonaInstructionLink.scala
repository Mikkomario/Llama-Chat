package vf.llama.database.access.single.instruction.link.persona

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.PersonaInstructionLinkDbFactory
import vf.llama.database.storable.instruction.PersonaInstructionLinkDbModel
import vf.llama.model.stored.instruction.PersonaInstructionLink

/**
  * Used for accessing individual persona instruction links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaInstructionLink 
	extends SingleRowModelAccess[PersonaInstructionLink] with NonDeprecatedView[PersonaInstructionLink] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = PersonaInstructionLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaInstructionLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted persona instruction link
	  * @return An access point to that persona instruction link
	  */
	def apply(id: Int) = DbSinglePersonaInstructionLink(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique persona instruction links.
	  * @return An access point to the persona instruction link that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniquePersonaInstructionLinkAccess(mergeCondition(additionalCondition))
}

