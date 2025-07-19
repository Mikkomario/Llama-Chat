package vf.llama.model.factory.statement

import utopia.logos.model.factory.text.TextPlacementFactory

/**
  * Common trait for statement link-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkFactory[+A] extends TextPlacementFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param statementId New statement id to assign
	  * @return Copy of this item with the specified statement id
	  */
	def withStatementId(statementId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withPlacedId(placedId: Int) = withStatementId(placedId)
}

