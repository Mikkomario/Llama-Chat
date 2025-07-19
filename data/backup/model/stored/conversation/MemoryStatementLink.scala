package vf.llama.model.stored.conversation

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.conversation.memory.link.statement.DbSingleMemoryStatementLink
import vf.llama.model.factory.conversation.MemoryStatementLinkFactoryWrapper
import vf.llama.model.partial.conversation.MemoryStatementLinkData
import vf.llama.model.partial.statement.StatementLinkData
import vf.llama.model.stored.statement.StoredStatementLinkLike

object MemoryStatementLink extends StoredFromModelFactory[MemoryStatementLinkData, MemoryStatementLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = MemoryStatementLinkData
	
	override protected def complete(model: AnyModel, data: MemoryStatementLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a memory statement link that has already been stored in the database
  * @param id id of this memory statement link in the database
  * @param data Wrapped memory statement link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MemoryStatementLink(id: Int, data: MemoryStatementLinkData) 
	extends MemoryStatementLinkFactoryWrapper[MemoryStatementLinkData, MemoryStatementLink] 
		with StatementLinkData with StoredStatementLinkLike[MemoryStatementLinkData, MemoryStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this memory statement link in the database
	  */
	def access = DbSingleMemoryStatementLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: MemoryStatementLinkData) = copy(data = data)
}

