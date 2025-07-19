package vf.llama.database.factory.instruction

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.instruction.InstructionVersionDbModel
import vf.llama.model.partial.instruction.InstructionVersionData
import vf.llama.model.stored.instruction.InstructionVersion

/**
  * Used for reading instruction version data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object InstructionVersionDbFactory 
	extends FromValidatedRowModelFactory[InstructionVersion] with FromTimelineRowFactory[InstructionVersion] 
		with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = InstructionVersionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		InstructionVersion(valid(this.model.id.name).getInt, 
			InstructionVersionData(valid(this.model.name.name).getString, 
			valid(this.model.enumValueId.name).int, valid(this.model.created.name).getInstant, 
			valid(this.model.deprecatedAfter.name).instant))
}

