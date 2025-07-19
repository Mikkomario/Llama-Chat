package vf.llama.database.factory.persona

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.persona.PersonaImageDbModel
import vf.llama.model.partial.persona.PersonaImageData
import vf.llama.model.stored.persona.PersonaImage

import scala.concurrent.duration.FiniteDuration

import java.util.concurrent.TimeUnit

/**
  * Used for reading persona image data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaImageDbFactory extends FromValidatedRowModelFactory[PersonaImage]
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = PersonaImageDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaImage(valid(this.model.id.name).getInt, 
			PersonaImageData(valid(this.model.animationId.name).getInt, 
			valid(this.model.fileName.name).getString, valid(this.model.orderIndex.name).getInt, 
			valid(this.model.customDuration.name).long.map { FiniteDuration(_, TimeUnit.MILLISECONDS) }))
}

