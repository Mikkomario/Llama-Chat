package vf.llama.database.factory.persona

import utopia.flow.generic.model.immutable.Model
import utopia.flow.parse.file.FileExtensions._
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.persona.PersonaAnimationDbModel
import vf.llama.model.partial.persona.PersonaAnimationData
import vf.llama.model.stored.persona.PersonaAnimation

import scala.concurrent.duration.FiniteDuration

import java.nio.file.Path
import java.util.concurrent.TimeUnit

/**
  * Used for reading persona animation data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaAnimationDbFactory extends FromValidatedRowModelFactory[PersonaAnimation]
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = PersonaAnimationDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaAnimation(valid(this.model.id.name).getInt, 
			PersonaAnimationData(valid(this.model.setId.name).getInt, 
			valid(this.model.relativeSubDirectory.name).getString: Path, 
			valid(this.model.defaultFrameDuration.name).long.map { FiniteDuration(_, 
			TimeUnit.MILLISECONDS) }))
}

