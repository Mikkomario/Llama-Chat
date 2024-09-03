package vf.llama.database.access.single.color

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.llama.database.factory.color.ColorDbFactory
import vf.llama.database.storable.color.ColorDbModel
import vf.llama.model.stored.color.Color

/**
  * Used for accessing individual colors
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbColor extends SingleRowModelAccess[Color] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = ColorDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ColorDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted color
	  * @return An access point to that color
	  */
	def apply(id: Int) = DbSingleColor(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique colors.
	  * @return An access point to the color that satisfies the specified condition
	  */
	private def distinct(condition: Condition) = UniqueColorAccess(condition)
}

