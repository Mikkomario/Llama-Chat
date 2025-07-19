package vf.llama.database.access.many.llm

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ViewFactory
import utopia.vault.sql.Condition
import vf.llama.database.factory.llm.LlmDbFactory
import vf.llama.model.stored.llm.Llm

object ManyLlmsAccess extends ViewFactory[ManyLlmsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyLlmsAccess = _ManyLlmsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyLlmsAccess(override val accessCondition: Option[Condition]) extends ManyLlmsAccess
}

/**
  * A common trait for access points which target multiple llms at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyLlmsAccess extends ManyLlmsAccessLike[Llm, ManyLlmsAccess] with ManyRowModelAccess[Llm]
{
	// IMPLEMENTED	--------------------
	
	override def factory = LlmDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyLlmsAccess = ManyLlmsAccess(condition)
}

