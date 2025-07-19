package vf.llama.database.access.many.color

import utopia.paradigm.angular.Angle
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{FilterableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.color.ColorDbFactory
import vf.llama.database.storable.color.ColorDbModel
import vf.llama.model.stored.color.Color

object ManyColorsAccess extends ViewFactory[ManyColorsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyColorsAccess = _ManyColorsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyColorsAccess(override val accessCondition: Option[Condition])
		 extends ManyColorsAccess
}

/**
  * A common trait for access points which target multiple colors at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyColorsAccess extends ManyRowModelAccess[Color] with FilterableView[ManyColorsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * hues of the accessible colors
	  */
	def hues(implicit connection: Connection) = 
		pullColumn(model.hue.column).map { v => v.getDouble }.map { v => Angle.degrees(v) }
	
	/**
	  * saturations of the accessible colors
	  */
	def saturations(implicit connection: Connection) = 
		pullColumn(model.saturation.column).map { v => v.getDouble }
	
	/**
	  * lightness values of the accessible colors
	  */
	def lightnessValues(implicit connection: Connection) = 
		pullColumn(model.lightness.column).map { v => v.getDouble }
	
	/**
	  * are predefined of the accessible colors
	  */
	def arePredefined(implicit connection: Connection) = 
		pullColumn(model.predefined.column).map { v => v.getBoolean }
	
	/**
	  * Unique ids of the accessible colors
	  */
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = ColorDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ColorDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): ManyColorsAccess = ManyColorsAccess(condition)
}

