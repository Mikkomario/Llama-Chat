package vf.llama.database.factory.persona

import utopia.flow.generic.model.immutable.Model
import utopia.flow.parse.file.FileExtensions._
import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.database.storable.persona.PersonaImageSetDbModel
import vf.llama.model.partial.persona.PersonaImageSetData
import vf.llama.model.stored.persona.PersonaImageSet

import java.nio.file.Path

/**
  * Used for reading persona image set data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaImageSetDbFactory 
	extends FromValidatedRowModelFactory[PersonaImageSet] with FromTimelineRowFactory[PersonaImageSet] 
		with Deprecatable
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = PersonaImageSetDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def nonDeprecatedCondition = model.nonDeprecatedCondition
	
	override def table = model.table
	
	override def timestamp = model.created
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaImageSet(valid(this.model.id.name).getInt, 
			PersonaImageSetData(valid(this.model.relativeDirectory.name).getString: Path, 
			valid(this.model.created.name).getInstant, valid(this.model.removedAfter.name).instant))
}

