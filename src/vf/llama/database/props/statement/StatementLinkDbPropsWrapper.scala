package vf.llama.database.props.statement

/**
  * Common trait for interfaces that provide access to statement link database properties by
  *  wrapping a StatementLinkDbProps
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkDbPropsWrapper extends StatementLinkDbProps
{
	// ABSTRACT	--------------------
	
	/**
	  * The wrapped statement link database properties
	  */
	protected def statementLinkDbProps: StatementLinkDbProps
	
	
	// IMPLEMENTED	--------------------
	
	override def id = statementLinkDbProps.id
	
	override def orderIndex = statementLinkDbProps.orderIndex
	
	override def parentId = statementLinkDbProps.parentId
	
	override def statementId = statementLinkDbProps.statementId
}

