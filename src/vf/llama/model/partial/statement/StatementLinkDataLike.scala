package vf.llama.model.partial.statement

import utopia.logos.model.partial.text.{TextPlacementData, TextPlacementDataLike}
import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for classes which provide read and copy access to statement link properties
  * @tparam Repr Implementing data class or data wrapper class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkDataLike[+Repr <: TextPlacementData] 
	extends HasStatementLinkProps with StatementLinkFactory[Repr] with TextPlacementDataLike[Repr] 
		with TextPlacementData
{
	// ABSTRACT	--------------------
	
	/**
	  * Builds a modified copy of this statement link
	  * @param parentId New parent id to assign. Default = current value.
	  * @param statementId New statement id to assign. Default = current value.
	  * @param orderIndex New order index to assign. Default = current value.
	  * @return A copy of this statement link with the specified properties
	  */
	def copyStatementLink(parentId: Int = parentId, statementId: Int = statementId, orderIndex: Int = orderIndex): Repr
	
	
	// IMPLEMENTED	--------------------
	
	override def copyTextPlacement(parentId: Int, placedId: Int, orderIndex: Int) = 
		copyStatementLink(parentId = parentId, statementId = placedId, orderIndex = orderIndex)
	
	override def withOrderIndex(orderIndex: Int) = copyStatementLink(orderIndex = orderIndex)
	
	override def withParentId(parentId: Int) = copyStatementLink(parentId = parentId)
	
	override def withStatementId(statementId: Int) = copyStatementLink(statementId = statementId)
}

