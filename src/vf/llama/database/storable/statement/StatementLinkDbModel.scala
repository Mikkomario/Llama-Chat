package vf.llama.database.storable.statement

import utopia.logos.database.storable.text.TextPlacementDbModel
import utopia.vault.model.immutable.Table
import vf.llama.database.props.statement.StatementLinkDbProps

object StatementLinkDbModel
{
	// OTHER	--------------------
	
	/**
	  * @param table The primarily targeted table
	  * @param props Targeted database properties
	  * @return A factory used for constructing statement link models using the specified configuration
	  */
	def factory(table: Table, props: StatementLinkDbProps) = StatementLinkDbModelFactory(table, props)
}

/**
  * Common trait for database interaction models dealing with statement links
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkDbModel extends StatementLinkDbModelLike[StatementLinkDbModel] with TextPlacementDbModel

