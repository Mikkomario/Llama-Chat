package vf.llama.database.access.single.llm

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.{NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.LlmDbFactory
import vf.llama.model.stored.llm.Llm

object UniqueLlmAccess extends ViewFactory[UniqueLlmAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueLlmAccess = _UniqueLlmAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueLlmAccess(override val accessCondition: Option[Condition])
		 extends UniqueLlmAccess
}

/**
  * A common trait for access points that return individual and distinct llms.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueLlmAccess 
	extends UniqueLlmAccessLike[Llm, UniqueLlmAccess] with SingleRowModelAccess[Llm] 
		with NullDeprecatableView[UniqueLlmAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = LlmDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueLlmAccess = UniqueLlmAccess(condition)
}

