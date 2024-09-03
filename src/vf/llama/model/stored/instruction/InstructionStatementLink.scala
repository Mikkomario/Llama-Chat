package vf.llama.model.stored.instruction

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.instruction.version.link.statement.DbSingleInstructionStatementLink
import vf.llama.model.factory.instruction.InstructionStatementLinkFactoryWrapper
import vf.llama.model.partial.instruction.InstructionStatementLinkData
import vf.llama.model.partial.statement.StatementLinkData
import vf.llama.model.stored.statement.StoredStatementLinkLike

object InstructionStatementLink 
	extends StoredFromModelFactory[InstructionStatementLinkData, InstructionStatementLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = InstructionStatementLinkData
	
	override protected def complete(model: AnyModel, data: InstructionStatementLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a instruction statement link that has already been stored in the database
  * @param id id of this instruction statement link in the database
  * @param data Wrapped instruction statement link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class InstructionStatementLink(id: Int, data: InstructionStatementLinkData) 
	extends InstructionStatementLinkFactoryWrapper[InstructionStatementLinkData, InstructionStatementLink] 
		with StatementLinkData 
		with StoredStatementLinkLike[InstructionStatementLinkData, InstructionStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this instruction statement link in the database
	  */
	def access = DbSingleInstructionStatementLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: InstructionStatementLinkData) = copy(data = data)
}

