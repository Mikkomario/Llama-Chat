package vf.llama.database.factory.meta

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.meta.CustomMetaInfoDbModel
import vf.llama.model.partial.meta.CustomMetaInfoData
import vf.llama.model.stored.meta.CustomMetaInfo

/**
  * Used for reading custom meta info data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object CustomMetaInfoDbFactory 
	extends FromValidatedRowModelFactory[CustomMetaInfo] with FromTimelineRowFactory[CustomMetaInfo] 
		with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = CustomMetaInfoDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		CustomMetaInfo(valid(this.model.id.name).getInt, 
			CustomMetaInfoData(valid(this.model.name.name).getString, valid(this.model.value.name).getString, 
			valid(this.model.created.name).getInstant, valid(this.model.deprecatedAfter.name).instant))
}

