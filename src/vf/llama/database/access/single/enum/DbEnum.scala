package vf.llama.database.access.single.`enum`

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.enum.EnumDbFactory
import vf.llama.database.storable.enum.EnumDbModel
import vf.llama.model.stored.enum.Enum

/**
  * Used for accessing individual enums
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbEnum extends SingleRowModelAccess[Enum] with NonDeprecatedView[Enum] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = EnumDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted enum
	  * @return An access point to that enum
	  */
	def apply(id: Int) = DbSingleEnum(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique enums.
	  * @return An access point to the enum that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueEnumAccess(mergeCondition(additionalCondition))
}

