package vf.llama.database.factory.persona

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.persona.PersonaImageSetLinkDbModel
import vf.llama.model.partial.persona.PersonaImageSetLinkData
import vf.llama.model.stored.persona.PersonaImageSetLink

/**
  * Used for reading persona image set link data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaImageSetLinkDbFactory 
	extends FromValidatedRowModelFactory[PersonaImageSetLink] with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = PersonaImageSetLinkDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaImageSetLink(valid(this.model.id.name).getInt, 
			PersonaImageSetLinkData(valid(this.model.personaId.name).getInt, 
			valid(this.model.imageSetId.name).getInt, valid(this.model.created.name).getInstant, 
			valid(this.model.removedAfter.name).instant))
}

