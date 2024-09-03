package vf.llama.database.factory.persona

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.persona.PersonaSettingsDbModel
import vf.llama.model.partial.persona.PersonaSettingsData
import vf.llama.model.stored.persona.PersonaSettings

/**
  * Used for reading persona settings data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaSettingsDbFactory 
	extends FromValidatedRowModelFactory[PersonaSettings] with FromTimelineRowFactory[PersonaSettings] 
		with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = PersonaSettingsDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaSettings(valid(this.model.id.name).getInt, 
			PersonaSettingsData(valid(this.model.personaId.name).getInt, 
			valid(this.model.llmVariantId.name).getInt, valid(this.model.name.name).getString, 
			valid(this.model.created.name).getInstant, valid(this.model.deprecatedAfter.name).instant))
}

