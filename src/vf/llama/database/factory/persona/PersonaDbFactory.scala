package vf.llama.database.factory.persona

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.persona.PersonaDbModel
import vf.llama.model.partial.persona.PersonaData
import vf.llama.model.stored.persona.Persona

/**
  * Used for reading persona data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaDbFactory extends FromValidatedRowModelFactory[Persona] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = PersonaDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		Persona(valid(this.model.id.name).getInt, PersonaData(valid(this.model.created.name).getInstant, 
			valid(this.model.deletedAfter.name).instant))
}

