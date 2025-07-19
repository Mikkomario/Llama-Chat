package vf.llama.database.access.many.statement

import utopia.logos.database.access.many.text.placement.ManyTextPlacementsAccessLike
import utopia.vault.database.Connection
import vf.llama.database.props.statement.StatementLinkDbProps

/**
  * A common trait for access points which target multiple statement links or similar instances at a time
  * @tparam A Type of read (statement links -like) instances
  * @tparam Repr Type of this access point
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyStatementLinksAccessLike[+A, +Repr] extends ManyTextPlacementsAccessLike[A, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	override protected def model: StatementLinkDbProps
	
	
	// COMPUTED	--------------------
	
	/**
	  * statement ids of the accessible statement links
	  */
	def statementIds(implicit connection: Connection) = placedIds
}

