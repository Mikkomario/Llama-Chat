package vf.llama.database.factory.persona

import utopia.bunnymunch.jawn.JsonBunny
import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.persona.PersonaParameterDbModel
import vf.llama.model.partial.persona.PersonaParameterData
import vf.llama.model.stored.persona.PersonaParameter

/**
  * Used for reading persona parameter data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaParameterDbFactory extends FromValidatedRowModelFactory[PersonaParameter]
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = PersonaParameterDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaParameter(valid(this.model.id.name).getInt, 
			PersonaParameterData(valid(this.model.settingsId.name).getInt, 
			valid(this.model.key.name).getString, 
			valid(this.model.value.name).mapIfNotEmpty { v => JsonBunny.sureMunch(v.getString) }))
}

