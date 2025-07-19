package vf.llama.database.access.single.instruction.version.link.statement

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.InstructionStatementLinkDbFactory
import vf.llama.database.storable.instruction.InstructionStatementLinkDbModel
import vf.llama.model.stored.instruction.InstructionStatementLink

/**
  * Used for accessing individual instruction statement links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbInstructionStatementLink 
	extends SingleRowModelAccess[InstructionStatementLink] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = InstructionStatementLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = InstructionStatementLinkDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted instruction statement link
	  * @return An access point to that instruction statement link
	  */
	def apply(id: Int) = DbSingleInstructionStatementLink(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique instruction statement links.
	  * @return An access point to the instruction statement link that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueInstructionStatementLinkAccess(condition)
}

