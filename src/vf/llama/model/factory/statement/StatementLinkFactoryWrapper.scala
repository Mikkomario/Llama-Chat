package vf.llama.model.factory.statement

import utopia.logos.model.factory.text.TextPlacementFactoryWrapper

/**
  * Common trait for classes that implement StatementLinkFactory by wrapping a StatementLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkFactoryWrapper[A <: StatementLinkFactory[A], +Repr] 
	extends StatementLinkFactory[Repr] with TextPlacementFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withStatementId(statementId: Int) = withPlacedId(statementId)
}

