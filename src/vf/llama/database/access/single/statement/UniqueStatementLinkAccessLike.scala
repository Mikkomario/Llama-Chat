package vf.llama.database.access.single.statement

import utopia.logos.database.access.single.text.placement.UniqueTextPlacementAccessLike
import utopia.vault.database.Connection
import vf.llama.database.props.statement.StatementLinkDbProps

/**
  * A common trait for access points which target individual statement links or similar items at a time
  * @tparam A Type of read (statement links -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueStatementLinkAccessLike[+A, +Repr] extends UniqueTextPlacementAccessLike[A, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model: StatementLinkDbProps
	
	
	// COMPUTED	--------------------
	
	/**
	  * Id of the statement made within the linked text / entity. 
	  * None if no statement link (or value) was found.
	  */
	def statementId(implicit connection: Connection) = placedId
}

