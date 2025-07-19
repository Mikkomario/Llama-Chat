package vf.llama.model.stored.statement

import utopia.logos.model.partial.text.TextPlacementData
import utopia.logos.model.stored.text.{StoredTextPlacementLike, TextPlacement}
import vf.llama.model.factory.statement.StatementLinkFactoryWrapper
import vf.llama.model.partial.statement.StatementLinkDataLike

/**
  * Common trait for statement links which have been stored in the database
  * @tparam Data Type of the wrapped data
  * @tparam Repr Implementing type
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StoredStatementLinkLike[Data <: StatementLinkDataLike[Data], +Repr <: TextPlacementData]
	extends StatementLinkFactoryWrapper[Data, Repr] with StatementLinkDataLike[Repr] 
		with StoredTextPlacementLike[Data, Repr] // with TextPlacement
{
	// IMPLEMENTED	--------------------
	
	override def statementId = data.statementId
	
	override protected def wrappedFactory = data
	
	override def copyStatementLink(parentId: Int, statementId: Int, orderIndex: Int) = 
		wrap(data.copyStatementLink(parentId, statementId, orderIndex))
}

