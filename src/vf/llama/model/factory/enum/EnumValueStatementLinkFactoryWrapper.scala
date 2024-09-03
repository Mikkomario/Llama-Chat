package vf.llama.model.factory.`enum`

import vf.llama.model.factory.statement.StatementLinkFactoryWrapper

/**
  * Common trait for classes that implement EnumValueStatementLinkFactory by wrapping
  *  a EnumValueStatementLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait EnumValueStatementLinkFactoryWrapper[A <: EnumValueStatementLinkFactory[A], +Repr] 
	extends EnumValueStatementLinkFactory[Repr] with StatementLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withEnumValueVersionId(enumValueVersionId: Int) = withParentId(enumValueVersionId)
}

