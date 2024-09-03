package vf.llama.database.access.many.instruction.link.thread

import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.access.many.instruction.link.ManyInstructionTargetingLinksAccessLike
import vf.llama.database.factory.instruction.ThreadInstructionLinkDbFactory
import vf.llama.database.storable.instruction.ThreadInstructionLinkDbModel
import vf.llama.model.stored.instruction.ThreadInstructionLink

object ManyThreadInstructionLinksAccess extends ViewFactory[ManyThreadInstructionLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyThreadInstructionLinksAccess = 
		_ManyThreadInstructionLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyThreadInstructionLinksAccess(override val accessCondition: Option[Condition]) 
		extends ManyThreadInstructionLinksAccess
}

/**
  * A common trait for access points which target multiple thread instruction links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyThreadInstructionLinksAccess 
	extends ManyInstructionTargetingLinksAccessLike[ThreadInstructionLink, ManyThreadInstructionLinksAccess] 
		with ChronoRowFactoryView[ThreadInstructionLink, ManyThreadInstructionLinksAccess] 
		with NullDeprecatableView[ManyThreadInstructionLinksAccess] 
		with ManyRowModelAccess[ThreadInstructionLink]
{
	// COMPUTED	--------------------
	
	/**
	  * thread ids of the accessible thread instruction links
	  */
	def threadIds(implicit connection: Connection) = targetIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ThreadInstructionLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = ThreadInstructionLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyThreadInstructionLinksAccess = 
		ManyThreadInstructionLinksAccess(condition)
}

