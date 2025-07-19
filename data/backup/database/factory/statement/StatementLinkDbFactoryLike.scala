package vf.llama.database.factory.statement

import utopia.logos.database.factory.text.TextPlacementDbFactoryLike
import vf.llama.database.props.statement.StatementLinkDbProps

/**
  * Common trait for factories which parse statement link data from database-originated models
  * @tparam A Type of read instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkDbFactoryLike[+A] extends TextPlacementDbFactoryLike[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * Database properties used when parsing column data
	  */
	override def dbProps: StatementLinkDbProps
}

