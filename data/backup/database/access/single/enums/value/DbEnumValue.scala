package vf.llama.database.access.single.enums.value

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.enums.EnumValueDbFactory
import vf.llama.database.storable.enums.EnumValueDbModel
import vf.llama.model.stored.enums.EnumValue

/**
  * Used for accessing individual enum values
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbEnumValue extends SingleRowModelAccess[EnumValue] with NonDeprecatedView[EnumValue] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = EnumValueDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumValueDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted enum value
	  * @return An access point to that enum value
	  */
	def apply(id: Int) = DbSingleEnumValue(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique enum values.
	  * @return An access point to the enum value that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueEnumValueAccess(mergeCondition(additionalCondition))
}

