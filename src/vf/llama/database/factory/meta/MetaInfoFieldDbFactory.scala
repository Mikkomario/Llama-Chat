package vf.llama.database.factory.meta

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.meta.MetaInfoFieldDbModel
import vf.llama.model.enumeration.AutomatedMetaInfo
import vf.llama.model.partial.meta.MetaInfoFieldData
import vf.llama.model.stored.meta.MetaInfoField

/**
  * Used for reading meta info field data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object MetaInfoFieldDbFactory extends FromValidatedRowModelFactory[MetaInfoField] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = MetaInfoFieldDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		MetaInfoField(valid(this.model.id.name).getInt, 
			MetaInfoFieldData(valid(this.model.automatedContent.name).int.flatMap(AutomatedMetaInfo.findForId), 
			valid(this.model.customInfoId.name).int, valid(this.model.created.name).getInstant, 
			valid(this.model.archivedAfter.name).instant))
}

