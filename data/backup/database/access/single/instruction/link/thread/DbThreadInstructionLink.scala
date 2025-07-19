package vf.llama.database.access.single.instruction.link.thread

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.ThreadInstructionLinkDbFactory
import vf.llama.database.storable.instruction.ThreadInstructionLinkDbModel
import vf.llama.model.stored.instruction.ThreadInstructionLink

/**
  * Used for accessing individual thread instruction links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbThreadInstructionLink 
	extends SingleRowModelAccess[ThreadInstructionLink] with NonDeprecatedView[ThreadInstructionLink] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = ThreadInstructionLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ThreadInstructionLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted thread instruction link
	  * @return An access point to that thread instruction link
	  */
	def apply(id: Int) = DbSingleThreadInstructionLink(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique thread instruction links.
	  * @return An access point to the thread instruction link that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueThreadInstructionLinkAccess(mergeCondition(additionalCondition))
}

