package vf.llama.database.access.single.instruction.version

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.instruction.InstructionVersionDbFactory
import vf.llama.database.storable.instruction.InstructionVersionDbModel
import vf.llama.model.stored.instruction.InstructionVersion

/**
  * Used for accessing individual instruction versions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbInstructionVersion 
	extends SingleRowModelAccess[InstructionVersion] with NonDeprecatedView[InstructionVersion] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = InstructionVersionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = InstructionVersionDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted instruction version
	  * @return An access point to that instruction version
	  */
	def apply(id: Int) = DbSingleInstructionVersion(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique instruction versions.
	  * @return An access point to the instruction version that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueInstructionVersionAccess(mergeCondition(additionalCondition))
}

