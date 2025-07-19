package vf.llama.database.factory.enums

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.enums.EnumDbModel
import vf.llama.model.partial.enums.EnumData
import vf.llama.model.stored.enums.Enum

/**
  * Used for reading enum data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object EnumDbFactory extends FromValidatedRowModelFactory[Enum] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = EnumDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		Enum(valid(this.model.id.name).getInt, EnumData(valid(this.model.name.name).getString, 
			valid(this.model.created.name).getInstant, valid(this.model.archivedAfter.name).instant))
}

