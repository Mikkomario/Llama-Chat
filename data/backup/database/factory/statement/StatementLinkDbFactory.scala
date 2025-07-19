package vf.llama.database.factory.statement

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.logos.database.factory.text.TextPlacementDbFactory
import utopia.vault.model.immutable.Table
import utopia.vault.sql.OrderBy
import vf.llama.database.props.statement.StatementLinkDbProps
import vf.llama.model.partial.statement.StatementLinkData
import vf.llama.model.stored.statement.StatementLink

object StatementLinkDbFactory
{
	// OTHER	--------------------
	
	/**
	  * @param table Table from which data is read
	  * @param dbProps Database properties used when reading column data
	  * @return A factory used for parsing statement links from database model data
	  */
	def apply(table: Table, dbProps: StatementLinkDbProps): StatementLinkDbFactory = 
		_StatementLinkDbFactory(table, dbProps)
	
	
	// NESTED	--------------------
	
	/**
	  * @param table Table from which data is read
	  * @param dbProps Database properties used when reading column data
	  */
	private case class _StatementLinkDbFactory(table: Table, dbProps: StatementLinkDbProps) 
		extends StatementLinkDbFactory
	{
		// IMPLEMENTED	--------------------
		
		override def defaultOrdering: Option[OrderBy] = None
		
		/**
		  * @param model Model from which additional data may be read
		  * @param id Id to assign to the read/parsed statement link
		  * @param parentId parent id to assign to the new statement link
		  * @param statementId statement id to assign to the new statement link
		  * @param orderIndex order index to assign to the new statement link
		  */
		override protected def apply(model: AnyModel, id: Int, parentId: Int, statementId: Int, orderIndex: Int) =
			StatementLink(id, StatementLinkData(parentId, statementId, orderIndex))
	}
}

/**
  * Common trait for factories which parse statement link data from database-originated models
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkDbFactory extends StatementLinkDbFactoryLike[StatementLink]

