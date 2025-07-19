package vf.llama.database.factory.llm

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.llm.LlmSizeVariantDbModel
import vf.llama.model.partial.llm.LlmSizeVariantData
import vf.llama.model.stored.llm.LlmSizeVariant

/**
  * Used for reading llm size variant data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object LlmSizeVariantDbFactory extends FromValidatedRowModelFactory[LlmSizeVariant] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = LlmSizeVariantDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		LlmSizeVariant(valid(this.model.id.name).getInt, 
			LlmSizeVariantData(valid(this.model.llmId.name).getInt, valid(this.model.suffix.name).getString, 
			valid(this.model.size.name).int, valid(this.model.created.name).getInstant, 
			valid(this.model.removedAfter.name).instant))
}

