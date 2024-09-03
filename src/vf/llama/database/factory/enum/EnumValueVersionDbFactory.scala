package vf.llama.database.factory.`enum`

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.enum.EnumValueVersionDbModel
import vf.llama.model.partial.enum.EnumValueVersionData
import vf.llama.model.stored.enum.EnumValueVersion

/**
  * Used for reading enum value version data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object EnumValueVersionDbFactory 
	extends FromValidatedRowModelFactory[EnumValueVersion] with FromTimelineRowFactory[EnumValueVersion] 
		with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = EnumValueVersionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		EnumValueVersion(valid(this.model.id.name).getInt, 
			EnumValueVersionData(valid(this.model.enumValueId.name).getInt, 
			valid(this.model.name.name).getString, valid(this.model.colorId.name).getInt, 
			valid(this.model.created.name).getInstant, valid(this.model.deprecatedAfter.name).instant))
}

