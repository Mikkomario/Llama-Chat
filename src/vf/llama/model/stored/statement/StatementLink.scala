package vf.llama.model.stored.statement

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.logos.model.partial.text.TextPlacementData
import utopia.logos.model.stored.text.StoredTextPlacementLike
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.model.partial.statement.StatementLinkData

object StatementLink extends StoredFromModelFactory[StatementLinkData, StatementLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = StatementLinkData
	
	override protected def complete(model: AnyModel, data: StatementLinkData) = 
		model("id").tryInt.map { apply(_, data) }
	
	
	// OTHER	--------------------
	
	/**
	  * Creates a new statement link
	  * @param id id of this statement link in the database
	  * @param data Wrapped statement link data
	  * @return statement link with the specified id and wrapped data
	  */
	def apply(id: Int, data: StatementLinkData): StatementLink = _StatementLink(id, data)
	
	
	// NESTED	--------------------
	
	/**
	  * Concrete implementation of the statement link trait
	  * @param id id of this statement link in the database
	  * @param data Wrapped statement link data
	  * @author Mikko Hilpinen
	  * @since 01.09.2024
	  */
	private case class _StatementLink(id: Int, data: StatementLinkData) extends StatementLink
	{
		// IMPLEMENTED	--------------------
		
		override def withId(id: Int) = copy(id = id)
		
		override protected def wrap(data: StatementLinkData) = copy(data = data)
	}
}

/**
  * Represents a statement link that has already been stored in the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLink 
	extends StoredStatementLinkLike[StatementLinkData, StatementLink] with StatementLinkData 
		with TextPlacementData with StoredTextPlacementLike[StatementLinkData, StatementLink]

