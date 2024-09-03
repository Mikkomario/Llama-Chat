package vf.llama.model.stored.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.instruction.link.thread.DbSingleThreadInstructionLink
import vf.llama.model.factory.instruction.ThreadInstructionLinkFactoryWrapper
import vf.llama.model.partial.instruction.{InstructionTargetingLinkData, ThreadInstructionLinkData}

object ThreadInstructionLink extends StoredFromModelFactory[ThreadInstructionLinkData, ThreadInstructionLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = ThreadInstructionLinkData
	
	override protected def complete(model: AnyModel, data: ThreadInstructionLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a thread instruction link that has already been stored in the database
  * @param id id of this thread instruction link in the database
  * @param data Wrapped thread instruction link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class ThreadInstructionLink(id: Int, data: ThreadInstructionLinkData) 
	extends ThreadInstructionLinkFactoryWrapper[ThreadInstructionLinkData, ThreadInstructionLink] 
		with InstructionTargetingLinkData 
		with StoredInstructionTargetingLinkLike[ThreadInstructionLinkData, ThreadInstructionLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this thread instruction link in the database
	  */
	def access = DbSingleThreadInstructionLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: ThreadInstructionLinkData) = copy(data = data)
}

