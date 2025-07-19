package vf.llama.database.factory.color

import utopia.flow.generic.model.immutable.Model
import utopia.paradigm.angular.Angle
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.sql.OrderBy
import vf.llama.database.storable.color.ColorDbModel
import vf.llama.model.partial.color.ColorData
import vf.llama.model.stored.color.Color

/**
  * Used for reading color data from the DB
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ColorDbFactory extends FromValidatedRowModelFactory[Color]
{
	// COMPUTED	--------------------
	
	/**
	  * Model that specifies how the data is read
	  */
	def model = ColorDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering: Option[OrderBy] = None
	
	override def table = model.table
	
	override protected def fromValidatedModel(valid: Model) = 
		Color(valid(this.model.id.name).getInt, 
			ColorData(Angle.degrees(valid(this.model.hue.name).getDouble), 
			valid(this.model.saturation.name).getDouble, valid(this.model.lightness.name).getDouble, 
			valid(this.model.predefined.name).getBoolean))
}

