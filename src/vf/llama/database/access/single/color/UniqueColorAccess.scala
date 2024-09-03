package vf.llama.database.access.single.color

import utopia.flow.generic.model.immutable.Value
import utopia.paradigm.angular.Angle
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.{FilterableView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.color.ColorDbFactory
import vf.llama.database.storable.color.ColorDbModel
import vf.llama.model.stored.color.Color

object UniqueColorAccess extends ViewFactory[UniqueColorAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): UniqueColorAccess = _UniqueColorAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _UniqueColorAccess(override val accessCondition: Option[Condition]) 
		extends UniqueColorAccess
}

/**
  * A common trait for access points that return individual and distinct colors.
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait UniqueColorAccess 
	extends SingleRowModelAccess[Color] with DistinctModelAccess[Color, Option[Color], Value] 
		with FilterableView[UniqueColorAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Hue of this color, as an angle. 
	  * None if no color (or value) was found.
	  */
	def hue(implicit connection: Connection) = Some(Angle.degrees(pullColumn(model.hue.column).getDouble))
	
	/**
	  * Saturation (color intensity) of this color. 
	  * None if no color (or value) was found.
	  */
	def saturation(implicit connection: Connection) = pullColumn(model.saturation.column).double
	
	/**
	  * Lightness of this color. Between 0 (black) and 1 (white). 
	  * None if no color (or value) was found.
	  */
	def lightness(implicit connection: Connection) = pullColumn(model.lightness.column).double
	
	/**
	  * Whether this is a predefined color. False if this is a user-defined color. 
	  * None if no color (or value) was found.
	  */
	def predefined(implicit connection: Connection) = pullColumn(model.predefined.column).boolean
	
	/**
	  * Unique id of the accessible color. None if no color was accessible.
	  */
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	protected def model = ColorDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ColorDbFactory
	
	override protected def self = this
	
	override def apply(condition: Condition): UniqueColorAccess = UniqueColorAccess(condition)
}

