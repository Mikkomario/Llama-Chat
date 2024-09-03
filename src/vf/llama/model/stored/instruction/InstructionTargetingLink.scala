package vf.llama.model.stored.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.model.partial.instruction.InstructionTargetingLinkData

object InstructionTargetingLink 
	extends StoredFromModelFactory[InstructionTargetingLinkData, InstructionTargetingLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = InstructionTargetingLinkData
	
	override protected def complete(model: AnyModel, data: InstructionTargetingLinkData) = 
		model("id").tryInt.map { apply(_, data) }
	
	
	// OTHER	--------------------
	
	/**
	  * Creates a new instruction targeting link
	  * @param id id of this instruction targeting link in the database
	  * @param data Wrapped instruction targeting link data
	  * @return instruction targeting link with the specified id and wrapped data
	  */
	def apply(id: Int, data: InstructionTargetingLinkData): InstructionTargetingLink = 
		_InstructionTargetingLink(id, data)
	
	
	// NESTED	--------------------
	
	/**
	  * Concrete implementation of the instruction targeting link trait
	  * @param id id of this instruction targeting link in the database
	  * @param data Wrapped instruction targeting link data
	  * @author Mikko Hilpinen
	  * @since 01.09.2024
	  */
	private case class _InstructionTargetingLink(id: Int, data: InstructionTargetingLinkData) 
		extends InstructionTargetingLink
	{
		// IMPLEMENTED	--------------------
		
		override def withId(id: Int) = copy(id = id)
		
		override protected def wrap(data: InstructionTargetingLinkData) = copy(data = data)
	}
}

/**
  * Represents a instruction targeting link that has already been stored in the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLink 
	extends StoredInstructionTargetingLinkLike[InstructionTargetingLinkData, InstructionTargetingLink] 
		with InstructionTargetingLinkData

