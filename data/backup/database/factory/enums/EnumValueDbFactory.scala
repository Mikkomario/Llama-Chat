package vf.llama.database.factory.enums

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.enums.EnumValueDbModel
import vf.llama.model.partial.enums.EnumValueData
import vf.llama.model.stored.enums.EnumValue

/**
  * Used for reading enum value data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object EnumValueDbFactory extends FromValidatedRowModelFactory[EnumValue] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = EnumValueDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		EnumValue(valid(this.model.id.name).getInt, EnumValueData(valid(this.model.enumId.name).getInt, 
			valid(this.model.created.name).getInstant, valid(this.model.archivedAfter.name).instant))
}

