package vf.llama.database.access.many.instruction.link.persona

import utopia.flow.collection.immutable.IntSet
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, NullDeprecatableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.access.many.instruction.link.ManyInstructionTargetingLinksAccessLike
import vf.llama.database.factory.instruction.PersonaInstructionLinkDbFactory
import vf.llama.database.storable.instruction.PersonaInstructionLinkDbModel
import vf.llama.model.stored.instruction.PersonaInstructionLink

object ManyPersonaInstructionLinksAccess extends ViewFactory[ManyPersonaInstructionLinksAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaInstructionLinksAccess = 
		_ManyPersonaInstructionLinksAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaInstructionLinksAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaInstructionLinksAccess
}

/**
  * A common trait for access points which target multiple persona instruction links at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaInstructionLinksAccess 
	extends ManyInstructionTargetingLinksAccessLike[PersonaInstructionLink, ManyPersonaInstructionLinksAccess] 
		with ChronoRowFactoryView[PersonaInstructionLink, ManyPersonaInstructionLinksAccess] 
		with NullDeprecatableView[ManyPersonaInstructionLinksAccess] 
		with ManyRowModelAccess[PersonaInstructionLink]
{
	// COMPUTED	--------------------
	
	/**
	  * persona ids of the accessible persona instruction links
	  */
	def personaIds(implicit connection: Connection) = targetIds
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaInstructionLinkDbFactory
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model = PersonaInstructionLinkDbModel
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyPersonaInstructionLinksAccess = 
		ManyPersonaInstructionLinksAccess(condition)
	
	
	// OTHER	--------------------
	
	/**
	  * @param personaId persona id to target
	  * @return Copy of this access point that only includes persona instruction links 
		with the specified persona id
	  */
	def forPersona(personaId: Int) = filter(model.personaId.column <=> personaId)
	
	/**
	  * @param personaIds Targeted persona ids
	  * 
		@return Copy of this access point that only includes persona instruction links where persona id is within
	  *  the specified value set
	  */
	def forPersonas(personaIds: IterableOnce[Int]) = filter(model
		.personaId.column.in(IntSet.from(personaIds)))
}

