package vf.llama.model.partial.statement

import utopia.logos.model.partial.text.HasTextPlacementProps

/**
  * Common trait for classes which provide access to statement link properties
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait HasStatementLinkProps extends HasTextPlacementProps
{
	// ABSTRACT	--------------------
	
	/**
	  * Id of the statement made within the linked text / entity
	  */
	def statementId: Int
	
	
	// IMPLEMENTED	--------------------
	
	override def placedId = statementId
}

