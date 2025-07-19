package vf.llama.database.storable.meta

import utopia.vault.model.immutable.Table
import vf.llama.database.props.meta.MetaInfoAvailabilityDbProps

object MetaInfoAvailabilityDbModel
{
	// OTHER	--------------------
	
	/**
	  * @param table The primarily targeted table
	  * @param props Targeted database properties
	  * 
		@return A factory used for constructing meta info availability models using the specified configuration
	  */
	def factory(table: Table, props: MetaInfoAvailabilityDbProps) = 
		MetaInfoAvailabilityDbModelFactory(table, props)
}

/**
  * Common trait for database interaction models dealing with meta info availabilities
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityDbModel extends MetaInfoAvailabilityDbModelLike[MetaInfoAvailabilityDbModel]

