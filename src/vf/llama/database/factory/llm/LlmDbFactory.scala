package vf.llama.database.factory.llm

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.llm.LlmDbModel
import vf.llama.model.partial.llm.LlmData
import vf.llama.model.stored.llm.Llm

/**
  * Used for reading llm data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object LlmDbFactory extends FromValidatedRowModelFactory[Llm] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = LlmDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		Llm(valid(this.model.id.name).getInt, LlmData(valid(this.model.name.name).getString, 
			valid(this.model.alias.name).getString, valid(this.model.created.name).getInstant, 
			valid(this.model.removedAfter.name).instant))
}

