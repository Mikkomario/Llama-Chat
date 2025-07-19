package vf.llama.database.access.single.llm

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.LlmDbFactory
import vf.llama.database.storable.llm.LlmDbModel
import vf.llama.model.stored.llm.Llm

/**
  * Used for accessing individual llms
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbLlm extends SingleRowModelAccess[Llm] with NonDeprecatedView[Llm] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = LlmDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = LlmDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted llm
	  * @return An access point to that llm
	  */
	def apply(id: Int) = DbSingleLlm(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique llms.
	  * @return An access point to the llm that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueLlmAccess(mergeCondition(additionalCondition))
}

