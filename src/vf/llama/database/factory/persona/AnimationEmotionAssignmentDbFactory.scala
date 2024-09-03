package vf.llama.database.factory.persona

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.persona.AnimationEmotionAssignmentDbModel
import vf.llama.model.partial.persona.AnimationEmotionAssignmentData
import vf.llama.model.stored.persona.AnimationEmotionAssignment

/**
  * Used for reading animation emotion assignment data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object AnimationEmotionAssignmentDbFactory 
	extends FromValidatedRowModelFactory[AnimationEmotionAssignment] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = AnimationEmotionAssignmentDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		AnimationEmotionAssignment(valid(this.model.id.name).getInt, 
			AnimationEmotionAssignmentData(valid(this.model.animationId.name).getInt, 
			valid(this.model.emotion.name).getString, valid(this.model.originLlmId.name).int, 
			valid(this.model.created.name).getInstant, valid(this.model.deprecatedAfter.name).instant))
}

